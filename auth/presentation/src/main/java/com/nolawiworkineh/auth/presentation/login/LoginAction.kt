package com.nolawiworkineh.auth.presentation.login

// sealed interface that handles all the user actions
sealed interface LoginAction {

    // **Password Visibility Toggle Action**: Triggered when the user clicks the "show/hide password" button.
    data object onTogglePasswordVisibility : LoginAction

    // **Login Button Click Action**: Triggered when the user clicks the "Login" button.
    data object onLoginClick : LoginAction

    // **Register Button Click Action**: Triggered when the user clicks the "Register" button to navigate to the registration screen.
    data object onRegisterClick : LoginAction
}
