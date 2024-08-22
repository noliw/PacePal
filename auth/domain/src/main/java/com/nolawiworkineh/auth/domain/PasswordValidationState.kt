package com.nolawiworkineh.auth.domain

data class PasswordValidationState(
    val hasMinLength: Boolean = false,
    val hasUppercase: Boolean = false,
    val hasLowercase: Boolean = false,
    val hasNumber: Boolean = false,
){
    val isValidPassword: Boolean
        get() = hasMinLength && hasUppercase && hasLowercase && hasNumber
}
