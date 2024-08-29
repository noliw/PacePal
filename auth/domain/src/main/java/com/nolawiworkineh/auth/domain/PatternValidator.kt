package com.nolawiworkineh.auth.domain

// **PatternValidator Interface**: Defines a contract for pattern validation across the application.
interface PatternValidator {
    // **Matches Function**: Determines if the input string matches the specified pattern.
    fun matches(value: String): Boolean
}
