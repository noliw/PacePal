package com.nolawiworkineh.auth.presentation.register

// **RegisterAction Interface**: Defines all possible user actions on the registration screen.
sealed interface RegisterAction {

    // **Toggle Password Visibility Action**: Represents the action of clicking to toggle the visibility of the password field.
    data object OnTogglePasswordVisibilityClick : RegisterAction

    // **Navigate to Login Action**: Represents the action of clicking to navigate back to the login screen.
    data object OnLoginClick : RegisterAction

    // **Submit Registration Action**: Represents the action of clicking to submit the registration form.
    data object OnRegisterClick : RegisterAction
}
