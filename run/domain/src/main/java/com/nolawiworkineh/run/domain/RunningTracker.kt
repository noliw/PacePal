@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package com.nolawiworkineh.run.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn

// Core class responsible for tracking runs and managing state related to running
class RunningTracker(
    // Injecting the LocationObserver to observe location updates
    private val locationObserver: LocationObserver
) {

    // StateFlow to track whether the app is currently observing the user's location
    private val isObservingLocation = MutableStateFlow(false)

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
            // CoroutineScope tied to the Main dispatcher, meaning it runs on the main thread
            CoroutineScope(Dispatchers.Main),
            // Lazily starts the flow when it's first collected
            SharingStarted.Lazily,
            // Initial value of the StateFlow, set to null since there's no location yet
            null
        )
}
