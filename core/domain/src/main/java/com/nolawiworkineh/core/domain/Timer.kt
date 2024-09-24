package com.nolawiworkineh.core.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

// Timer object to emit elapsed time at regular intervals
object Timer {
    // Function that tracks time and emits the duration as a Flow
    fun timeAndEmit(): Flow<Duration> {
        return flow {
            // Capturing the time when the flow starts emitting
            var lastEmitTime = System.currentTimeMillis()

            // Infinite loop to continuously emit elapsed time
            while (true) {
                // Delay for 200 milliseconds before emitting the next value
                delay(200L)

                // Get the current time and calculate elapsed time since last emission
                val currentTime = System.currentTimeMillis()
                val elapsedTime = currentTime - lastEmitTime

                // Emit the elapsed time in milliseconds as a Kotlin Duration object
                emit(elapsedTime.milliseconds)

                // Update the lastEmitTime for the next iteration
                lastEmitTime = currentTime
            }
        }
    }
}