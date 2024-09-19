package com.nolawiworkineh.run.presentation.active_run

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class ActiveRunViewModel: ViewModel() {

    // **State Property**: Holds the current state of the active run (time, distance, etc.).
    var state by mutableStateOf(ActiveRunState())
        private set  // **Private Setter**: Prevents external modification of the state.

    // **Event Channel**: A channel that sends one-time events (like errors or success messages) to the UI.
    private val eventChannel = Channel<ActiveRunEvent>()
    // **Expose Events as Flow**: Converts the eventChannel into a Flow so the UI can observe it.
    val events = eventChannel.receiveAsFlow()

    // **onAction Function**: Handles different user actions, like starting or stopping a run.
    fun onAction(action: ActiveRunAction) {

    }
}
