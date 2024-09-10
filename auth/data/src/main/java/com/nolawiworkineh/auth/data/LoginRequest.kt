package com.nolawiworkineh.auth.data

import kotlinx.serialization.Serializable


@Serializable
data class LoginRequest(
    // **Email**: The user's email address used for login.
    val email: String,

    // **Password**: The user's password used for login.
    val password: String
)
