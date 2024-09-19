package com.nolawiworkineh.run.domain

import com.nolawiworkineh.core.domain.location.LocationTimestamp
import kotlin.time.Duration

//Represents the data associated with a run, including distance, pace, and a list of location points.
data class RunData(
    val distanceMeters: Int = 0,  // The total distance covered during the run in meters.
    val pace: Duration = Duration.ZERO,  // The current pace (speed) of the user during the run.
    val locations: List<List<LocationTimestamp>> = emptyList()  // A list of location points captured during the run, organized by timestamps.
)

