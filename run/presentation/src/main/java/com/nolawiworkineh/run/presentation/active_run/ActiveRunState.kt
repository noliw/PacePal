package com.nolawiworkineh.run.presentation.active_run

import com.nolawiworkineh.core.domain.location.Location
import com.nolawiworkineh.run.domain.RunData
import kotlin.time.Duration


data class ActiveRunState(
    val elapsedTime: Duration = Duration.ZERO,  // The total time elapsed since the run started.
    val runData: RunData = RunData(),  // The data of the current run, such as distance, pace, and location history.
    val isTracking: Boolean = false,  // Whether the app should actively track the user's movement.
    val hasStartedRunning: Boolean = false,  // Indicates if the user has started running.
    val currentLocation: Location? = null,  // The user's current location (latitude, longitude).
    val isRunFinished: Boolean = false,  // Indicates if the user has finished their run.
    val isSavingRun: Boolean = false,  // Whether the app is currently saving the run data.

    // properties to handle permission rationale visibility
    val showLocationRationale: Boolean = false,// Indicates if we should show a rationale(explanation) for location permissions.
    val showNotificationRationale: Boolean = false // Indicates if we should show a rationale(explanation) for notification permissions.
)

