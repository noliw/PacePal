package com.nolawiworkineh.auth.domain

// **UserDataValidator Class**: A utility class for validating user data, particularly passwords.
class UserDataValidator(private val patternValidator: PatternValidator) {

    fun isValidEmail(email: String): Boolean {
        return patternValidator.matches(email.trim())
    }

    fun validatePassword(password: String): PasswordValidationState {
        val hasMinLength = password.length >= MIN_PASSWORD_LENGTH
        val hasUppercase = password.any { it.isUpperCase() }
        val hasLowercase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }

        return PasswordValidationState(
            hasMinLength = hasMinLength,
            hasUppercase = hasUppercase,
            hasLowercase = hasLowercase,
            hasNumber = hasDigit
        )

    }

    // **Companion Object**: A block that allows the definition of constants and utility methods associated with the class.
    companion object {
        // **Minimum Password Length Constant**: Defines the minimum number of characters required for a valid password.
        const val MIN_PASSWORD_LENGTH = 9
    }
}
