@file:OptIn(ExperimentalFoundationApi::class)

package com.nolawiworkineh.auth.presentation.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState

// this class is used to hold/store the state of the login screen.
data class LoginState(
    // **Email State**: Holds the current value and validation state of the email input field.
    val email: TextFieldState = TextFieldState(),

    // **Password State**: Holds the current value and validation state of the password input field.
    val password: TextFieldState = TextFieldState(),

    // **Password Visibility Flag**: Controls whether the password is visible or hidden (used for toggling password visibility).
    val isPasswordVisible: Boolean = false,

    // **Login Progress Flag**: Indicates whether the login process is currently happening (used to show a loading spinner).
    val isLoginInProgress: Boolean = false,

    // **Can Login Flag**: Determines if the login button should be enabled or disabled (based on email/password validation).
    val canLogin: Boolean = false
)

