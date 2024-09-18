package com.nolawiworkineh.run.presentation.active_run

import com.nolawiworkineh.presentation.ui.UiText

sealed interface ActiveRunEvent {
    data class Error(val error: UiText): ActiveRunEvent
    data object RunSaved: ActiveRunEvent
}