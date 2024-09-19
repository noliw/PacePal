package com.nolawiworkineh.core.domain.location

import kotlin.time.Duration

data class LocationTimestamp(
    val location: LocationWithAltitude,  // The user's location (latitude, longitude, altitude) at a specific point in time.
    val durationTimestamp: Duration  // The time elapsed since the start of the run when this location was captured.
)

