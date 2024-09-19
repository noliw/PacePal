package com.nolawiworkineh.run.presentation.active_run

import com.nolawiworkineh.presentation.ui.UiText

// events that we send from the viewModel to our screen
sealed interface ActiveRunEvent {
    // **Error Event**: Represents an error state during the active run, typically used to display error messages to the user.
    data class Error(val error: UiText) : ActiveRunEvent

    // **RunSaved Event**: This event is triggered when the run data is successfully saved.
    data object RunSaved : ActiveRunEvent
}


