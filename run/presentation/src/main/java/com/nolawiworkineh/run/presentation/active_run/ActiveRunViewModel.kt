package com.nolawiworkineh.run.presentation.active_run

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nolawiworkineh.core.domain.location.Location
import com.nolawiworkineh.core.domain.run.Run
import com.nolawiworkineh.run.domain.LocationDataCalculator
import com.nolawiworkineh.run.domain.RunningTracker
import com.nolawiworkineh.run.presentation.active_run.service.ActiveRunService
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime

class ActiveRunViewModel(
    // Injecting the RunningTracker
    private val runningTracker: RunningTracker
): ViewModel() {

    // **State Property**: Holds the current state of the active run, like elapsed time, run data, permissions, etc.
    var state by mutableStateOf(ActiveRunState(
        isTracking = ActiveRunService.isServiceActive && runningTracker.isTracking.value,
        hasStartedRunning = ActiveRunService.isServiceActive
    ))
        private set  // **Private Setter**: The state can only be modified inside the ViewModel to ensure controlled changes.

    // **Event Channel**: Used to send one-time events like errors or success messages to the UI.
    private val eventChannel = Channel<ActiveRunEvent>()

    // **Flow for Events**: The UI listens to this flow for events like showing errors or handling success messages.
    val events = eventChannel.receiveAsFlow()

    private val shouldTrack = snapshotFlow { state.isTracking }
        .stateIn(viewModelScope, SharingStarted.Lazily, state.isTracking)

    // **Location Permission Tracking**: Tracks if the user has granted location permission using a StateFlow.
    private val hasUserGrantedLocationPermission = MutableStateFlow(false)

    private val isTracking = combine(
        shouldTrack,
        hasUserGrantedLocationPermission
    ) { shouldTrack, hasUserGrantedLocationPermission ->
        shouldTrack && hasUserGrantedLocationPermission
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    // Initializer block where we observe the location permission state
    init {
        // Observe changes in location permission
        hasUserGrantedLocationPermission.onEach { hasPermission ->
                // If the app has location permission, start observing location updates
                if (hasPermission) {
                    runningTracker.startObservingLocation()
                }
                // If permission is not granted, stop observing location updates
                else {
                    runningTracker.stopObservingLocation()
                }
            }
            .launchIn(viewModelScope) // Launch in the ViewModel's scope to handle lifecycle automatically


        isTracking
            .onEach { isTracking ->
                runningTracker.setIsTracking(isTracking)
            }
            .launchIn(viewModelScope)

        runningTracker
            .currentLocation
            .onEach {
                state = state.copy(currentLocation = it?.location)
            }
            .launchIn(viewModelScope)

        runningTracker
            .runData
            .onEach {
                state = state.copy(runData = it)
            }
            .launchIn(viewModelScope)

        runningTracker
            .elapsedTime
            .onEach {
                state = state.copy(elapsedTime = it)
            }
            .launchIn(viewModelScope)
    }

    // **onAction Function**: Handles the actions taken by the user, like starting/stopping a run or dealing with permissions.
    fun onAction(action: ActiveRunAction) {
        when(action) {
            ActiveRunAction.OnFinishRunClick -> {
                state = state.copy(
                    isRunFinished = true,
                    isSavingRun = true
                )
            }

            ActiveRunAction.OnResumeRunClick -> {
                state = state.copy(isTracking = true)
            }
            ActiveRunAction.OnBackClick -> {
                state = state.copy(isTracking = false)
            }
            ActiveRunAction.OnToggleRunClick -> {
                state = state.copy(
                    hasStartedRunning = true,
                    isTracking = !state.isTracking
                )
            }
            // **Submit Location Permission Info**: Updates the location permission state and whether to show rationale.
            is ActiveRunAction.SubmitLocationPermissionInfo -> {
                // Update the StateFlow tracking location permission.
                hasUserGrantedLocationPermission.value = action.acceptedLocationPermission

                // Update the state with the new location rationale information.
                state = state.copy(
                    showLocationRationale = action.showLocationRationale
                )
            }
            // **Submit Notification Permission Info**: Updates the state to track notification permission rationale.
            is ActiveRunAction.SubmitNotificationPermissionInfo -> {
                state = state.copy(
                    showNotificationRationale = action.showNotificationPermissionRationale
                )
            }
            // **Dismiss Rationale Dialog**: Clears any displayed permission rationale dialogs.
            is ActiveRunAction.DismissRationaleDialog -> {
                state = state.copy(
                    showNotificationRationale = false,  // Hide the notification permission rationale dialog.
                    showLocationRationale = false       // Hide the location permission rationale dialog.
                )
            }
            is ActiveRunAction.OnRunProcessed -> {
                finishRun(action.mapPictureBytes)
            }
            // **Default Case**: If no matching action is found, do nothing.
            else -> Unit
        }
    }

    private fun finishRun(mapPictureBytes: ByteArray) {
        val locations = state.runData.locations
        if(locations.isEmpty() || locations.first().size <= 1) {
            state = state.copy(isSavingRun = false)
            return
        }

        viewModelScope.launch {
            val run = Run(
                id = null,
                duration = state.elapsedTime,
                dateTimeUtc = ZonedDateTime.now()
                    .withZoneSameInstant(ZoneId.of("UTC")),
                distanceMeters = state.runData.distanceMeters,
                location = state.currentLocation ?: Location(0.0, 0.0),
                maxSpeedKmh = LocationDataCalculator.getMaxSpeedKmh(locations),
                totalElevationMeters = LocationDataCalculator.getTotalElevationMeters(locations),
                mapPictureUrl = null
            )

            // Save run in repository
            runningTracker.finishRun()
            state = state.copy(isSavingRun = false)
        }
    }

    override fun onCleared() {
        super.onCleared()
        if(!ActiveRunService.isServiceActive) {
            runningTracker.stopObservingLocation()
        }
    }
}
