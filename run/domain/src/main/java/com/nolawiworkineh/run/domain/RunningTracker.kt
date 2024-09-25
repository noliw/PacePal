@file:OptIn(ExperimentalCoroutinesApi::class)

package com.nolawiworkineh.run.domain

import com.nolawiworkineh.core.domain.Timer
import com.nolawiworkineh.core.domain.location.LocationTimestamp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlin.math.roundToInt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

// Core class responsible for tracking runs and managing state related to running
@OptIn(ExperimentalCoroutinesApi::class)
class RunningTracker(
    // Injecting the LocationObserver to observe location updates
    private val locationObserver: LocationObserver,
    // Injecting the application-wide CoroutineScope
    private val applicationScope: CoroutineScope
) {

    // StateFlow to hold the data of the current run
    private val _runData = MutableStateFlow(RunData()) // RunData holds distance, pace, etc.
    val runData = _runData.asStateFlow() // Exposing the runData as an immutable flow

    // StateFlow to track whether the app is currently tracking the run
    private val isTracking = MutableStateFlow(false)

    // StateFlow to track whether the app is observing the user's location
    private val isObservingLocation = MutableStateFlow(false)

    // StateFlow to hold the elapsed time during the run
    private val _elapsedTime = MutableStateFlow(Duration.ZERO)
    val elapsedTime = _elapsedTime.asStateFlow() // Exposing elapsed time to observers

    // Flow that emits the current location based on whether location observation is enabled
    val currentLocation = isObservingLocation
        // flatMapLatest switches the flow to the latest location data when observation is enabled
        .flatMapLatest { isObservingLocation ->
            if (isObservingLocation) {
                // If the app is observing location, request location updates every second (1000 milliseconds)
                locationObserver.observeLocation(1000L)
            } else {
                // If not observing location, return an empty flow
                flowOf()
            }
        }
        // stateIn converts the flow into a StateFlow, sharing the latest location in the main UI thread
        .stateIn(
            // Using the injected application-wide scope instead of manually creating one
            applicationScope,
            // Lazily starts the flow when it's first collected
            SharingStarted.Lazily,
            // Initial value of the StateFlow, set to null since there's no location yet
            null
        )

    // Initialization block to set up timers and tracking logic
    init {
        // Start the timer if tracking is active, and update the elapsed time
        isTracking.flatMapLatest { isTracking ->
                if (isTracking) {
                    // Start the timer if tracking is true
                    Timer.timeAndEmit()
                } else {
                    // If tracking is false, emit an empty flow (no time updates)
                    flowOf()
                }
            }
            .onEach { elapsed ->
                // Add the emitted time from the timer to the elapsed time
                _elapsedTime.value += elapsed
            }
            .launchIn(applicationScope) // Launch the flow in the application scope

        // Start observing the current location and filter out any null values from the location flow
        currentLocation
            .filterNotNull()
            // Combine the location updates with the isTracking flag to control whether tracking is active
            .combineTransform(isTracking) { location, isTracking ->
                // Only emit location data if tracking is currently active
                if (isTracking) {
                    emit(location)
                }
            }
            // Combine the emitted location with the elapsed time (_elapsedTime) to create a LocationTimestamp object
            .zip(_elapsedTime) { location, elapsedTime ->
                // Emit a LocationTimestamp containing both the location and the elapsed time
                LocationTimestamp(
                    location = location,
                    durationTimestamp = elapsedTime
                )
            }
            // Process each location and update the run data
            .onEach { location ->
                // Get the current list of locations from runData
                val currentLocations = runData.value.locations

                // Check if there are previous locations, and append the new location to the last list
                val lastLocationsList = if (currentLocations.isNotEmpty()) {
                    currentLocations.last() + location // Add the current location to the last segment
                } else listOf(location) // If there are no previous locations, start a new list

                // Replace the last entry of the locations list with the updated list of locations
                val newLocationsList = currentLocations.replaceLast(lastLocationsList)

                // Calculate the total distance covered based on the updated locations
                val distanceMeters = LocationDataCalculator.getTotalDistanceMeters(
                    locations = newLocationsList
                )

                // Convert the total distance from meters to kilometers
                val distanceKm = distanceMeters / 1000.0

                // Get the elapsed time from the current location timestamp
                val currentDuration = location.durationTimestamp

                // Calculate the average pace (time per kilometer) in seconds per kilometer
                val avgSecondsPerKm = if (distanceKm == 0.0) {
                    0 // If no distance is covered, return 0
                } else {
                    (currentDuration.inWholeSeconds / distanceKm).roundToInt() // Calculate the pace
                }

                // Update the _runData with the new distance, pace, and location list
                _runData.update {
                    RunData(
                        distanceMeters = distanceMeters, // Update the total distance covered
                        pace = avgSecondsPerKm.seconds,  // Update the average pace in seconds per km
                        locations = newLocationsList     // Update the list of locations
                    )
                }
            }
            // Launch the flow in the application-wide coroutine scope so it remains active throughout the app's lifecycle
            .launchIn(applicationScope)

    }

    // Function to start or stop tracking the run
    fun setIsTracking(isTracking: Boolean) {
        this.isTracking.value = isTracking
    }

    // Function to start observing location
    fun startObservingLocation() {
        isObservingLocation.value = true
    }

    // Function to stop observing location
    fun stopObservingLocation() {
        isObservingLocation.value = false
    }


}

private fun <T> List<List<T>>.replaceLast(replacement: List<T>): List<List<T>> {
    if(this.isEmpty()) {
        return listOf(replacement)
    }
    return this.dropLast(1) + listOf(replacement)
}
