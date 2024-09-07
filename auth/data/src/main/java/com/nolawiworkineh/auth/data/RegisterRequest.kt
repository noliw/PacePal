package com.nolawiworkineh.auth.data

import kotlinx.serialization.Serializable

// **RegisterRequest Data Class**: Represents the request body for registering a user.
@Serializable  // Marks the class as serializable, so it can be converted to JSON when sent in the HTTP request.
data class RegisterRequest(
    val email: String,  // The user's email
    val password: String  // The user's password
)

