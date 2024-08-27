package com.nolawiworkineh.auth.domain

// **PasswordValidationState Data Class**: Holds the validation state for a password.
data class PasswordValidationState(
    // **Minimum Length**: Indicates if the password meets the minimum length requirement.
    val hasMinLength: Boolean = false,
    // **Uppercase Letter**: Indicates if the password contains at least one uppercase letter.
    val hasUppercase: Boolean = false,
    // **Lowercase Letter**: Indicates if the password contains at least one lowercase letter.
    val hasLowercase: Boolean = false,
    // **Number**: Indicates if the password contains at least one number.
    val hasNumber: Boolean = false,
) {
    // **isValidPassword Property**: Checks if the password meets all validation criteria.
    val isValidPassword: Boolean
        get() = hasMinLength && hasUppercase && hasLowercase && hasNumber
}

