package com.nolawiworkineh.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import com.nolawiworkineh.auth.domain.PasswordValidationState


// **RegisterState Data Class**: Manages the state of the registration screen.
@OptIn(ExperimentalFoundationApi::class)
data class RegisterState(
    // **Email State**: Tracks the current state of the email input field.
    val email: TextFieldState = TextFieldState(),
    // **Email Validation**: Indicates whether the entered email is valid.
    val isEmailValid: Boolean = false,
    // **Password State**: Tracks the current state of the password input field.
    val password: TextFieldState = TextFieldState(),
    // **Password Visibility**: Tracks whether the password is currently visible or hidden.
    val isPasswordVisible: Boolean = false,
    // **Password Validation State**: Holds the validation state of the password.
    val passwordValidationState: PasswordValidationState = PasswordValidationState(),
    // **Registering State**: Indicates whether the app is currently registering the user.
    val isRegistering: Boolean = false,
    // **Can Register**: Indicates whether the user can proceed with registration.
    val canRegister: Boolean = passwordValidationState.isValidPassword && !isRegistering
)

