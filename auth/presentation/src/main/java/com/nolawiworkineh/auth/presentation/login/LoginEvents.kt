package com.nolawiworkineh.auth.presentation.login

import com.nolawiworkineh.presentation.ui.UiText

sealed interface LoginEvent {
    data class Error(val errorMessage: UiText) : LoginEvent
    data object LoginSuccess : LoginEvent
}