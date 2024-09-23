package com.nolawiworkineh.run.domain

import com.nolawiworkineh.core.domain.location.LocationWithAltitude
import kotlinx.coroutines.flow.Flow

// defines the contract(instruction), the WHAT, for observing the user’s location at regular intervals
interface LocationObserver {
    // the observeLocation Function Starts observing the user's location at the specified interval (in milliseconds).
    // Returns a Flow: The function returns a Flow of LocationWithAltitude objects, which represent the user's location over time.
    fun observeLocation(interval: Long): Flow<LocationWithAltitude>
}
