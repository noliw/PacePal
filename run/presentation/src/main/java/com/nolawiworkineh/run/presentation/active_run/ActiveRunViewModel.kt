package com.nolawiworkineh.run.presentation.active_run

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nolawiworkineh.run.domain.RunningTracker
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import timber.log.Timber

class ActiveRunViewModel(
    // Injecting the RunningTracker
    private val runningTracker: RunningTracker
): ViewModel() {

    // **State Property**: Holds the current state of the active run, like elapsed time, run data, permissions, etc.
    var state by mutableStateOf(ActiveRunState())
        private set  // **Private Setter**: The state can only be modified inside the ViewModel to ensure controlled changes.

    // **Event Channel**: Used to send one-time events like errors or success messages to the UI.
    private val eventChannel = Channel<ActiveRunEvent>()

    // **Flow for Events**: The UI listens to this flow for events like showing errors or handling success messages.
    val events = eventChannel.receiveAsFlow()

    // **Location Permission Tracking**: Tracks if the user has granted location permission using a StateFlow.
    private val _hasUserGrantedLocationPermission = MutableStateFlow(false)

    // Initializer block where we observe the location permission state
    init {
        // Observe changes in location permission
        _hasUserGrantedLocationPermission.onEach { hasPermission ->
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

        runningTracker
            .currentLocation
            .onEach { location ->
                Timber.d("New location: $location")
            }
            .launchIn(viewModelScope)
    }

    // **onAction Function**: Handles the actions taken by the user, like starting/stopping a run or dealing with permissions.
    fun onAction(action: ActiveRunAction) {
        when(action) {
            // **Finish Run Action**: Placeholder for the logic to finish a run.
            ActiveRunAction.OnFinishRunClick -> {
                // Code to finish run will go here.
            }

            // **Resume Run Action**: Placeholder for the logic to resume a paused run.
            ActiveRunAction.OnResumeRunClick -> {
                // Code to resume run will go here.
            }

            // **Toggle Run Action**: Placeholder for the logic to toggle between starting and stopping a run.
            ActiveRunAction.OnToggleRunClick -> {
                // Code to toggle run will go here.
            }
            // **Submit Location Permission Info**: Updates the location permission state and whether to show rationale.
            is ActiveRunAction.SubmitLocationPermissionInfo -> {
                // Update the StateFlow tracking location permission.
                _hasUserGrantedLocationPermission.value = action.acceptedLocationPermission

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
            // **Default Case**: If no matching action is found, do nothing.
            else -> Unit
        }
    }
}
