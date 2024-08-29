package com.nolawiworkineh.auth.domain

// **UserDataValidator Class**: This class handles validation for user data like email addresses and passwords.
class UserDataValidator(private val patternValidator: PatternValidator) {

    // **isValidEmail Function**: Validates the email address using the provided PatternValidator.
    fun isValidEmail(email: String): Boolean {
        // **email.trim()**: Removes any leading or trailing whitespace from the email string to ensure a clean input.
        return patternValidator.matches(email.trim()) // **patternValidator.matches(email)**: Uses the PatternValidator to check if the trimmed email matches the expected pattern.
    }

    // **validatePassword Function**: Validates the password against multiple criteria, such as length, uppercase and lowercase letters, and digits.
    fun validatePassword(password: String): PasswordValidationState {
        // **hasMinLength**: Checks if the password has at least the minimum required length.
        val hasMinLength = password.length >= MIN_PASSWORD_LENGTH
        // **hasUppercase**: Checks if the password contains at least one uppercase letter.
        val hasUppercase = password.any { it.isUpperCase() }
        // **hasLowercase**: Checks if the password contains at least one lowercase letter.
        val hasLowercase = password.any { it.isLowerCase() }
        // **hasDigit**: Checks if the password contains at least one digit.
        val hasDigit = password.any { it.isDigit() }

        // **PasswordValidationState Object**: Encapsulates the results of the password validation checks.
        return PasswordValidationState(
            hasMinLength = hasMinLength, // **hasMinLength**: Indicates whether the password meets the minimum length requirement.
            hasUppercase = hasUppercase, // **hasUppercase**: Indicates whether the password contains at least one uppercase letter.
            hasLowercase = hasLowercase, // **hasLowercase**: Indicates whether the password contains at least one lowercase letter.
            hasNumber = hasDigit // **hasDigit**: Indicates whether the password contains at least one digit.
        )
    }

    // **Companion Object**: A block that allows the definition of constants and utility methods associated with the class.
    companion object {
        // **MIN_PASSWORD_LENGTH Constant**: Defines the minimum number of characters required for a valid password.
        const val MIN_PASSWORD_LENGTH = 9
    }
}

