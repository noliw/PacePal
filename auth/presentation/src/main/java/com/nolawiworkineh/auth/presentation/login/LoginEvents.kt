package com.nolawiworkineh.auth.presentation.login

import com.nolawiworkineh.presentation.ui.UiText

// this interface is used to handle the different messages we get from our server to the ui
sealed interface LoginEvent {

    // **Error Event**: Triggered when there is an error during the login process (e.g., invalid credentials, network error).
    data class Error(val errorMessage: UiText) : LoginEvent

    // **Login Success Event**: Triggered when the login process is successful and the user is authenticated.
    data object LoginSuccess : LoginEvent
}
