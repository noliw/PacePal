@file:OptIn(ExperimentalCoroutinesApi::class)

package com.nolawiworkineh.run.domain

import com.nolawiworkineh.core.domain.Timer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlin.time.Duration

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
