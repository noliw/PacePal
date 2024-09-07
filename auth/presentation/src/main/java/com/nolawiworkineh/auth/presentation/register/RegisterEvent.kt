package com.nolawiworkineh.auth.presentation.register

import com.nolawiworkineh.presentation.ui.UiText

// **RegisterAction Interface**: Defines all possible user actions on the registration screen.
sealed interface RegisterEvent {

    // **Toggle Password Visibility Action**: Represents the action of clicking to toggle the visibility of the password field.
    data object RegistrationSuccess : RegisterEvent

    // **Navigate to Login Action**: Represents the action of clicking to navigate back to the login screen.
    data class Error(val error: UiText) : RegisterEvent

}
