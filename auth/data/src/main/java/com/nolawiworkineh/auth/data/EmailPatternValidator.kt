package com.nolawiworkineh.auth.data

import android.util.Patterns
import com.nolawiworkineh.auth.domain.PatternValidator

// **EmailPatternValidator Object**: A platform-specific implementation of PatternValidator for email validation.
object EmailPatternValidator: PatternValidator {
    // **Matches Function**: Uses Android's Patterns class to validate if the input string is a valid email.
    override fun matches(value: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }
}

