package com.nolawiworkineh.core.domain.AuthInfo

// **AuthInfo**: A data class that holds the user's authentication tokens and their user ID.
data class AuthInfo(
    // **Access Token**: A token used for making authenticated API requests. This expires after a short period.
    val accessToken: String,

    // **Refresh Token**: A token used to generate a new access token when the current one expires.
    val refreshToken: String,

    // **User ID**: The unique identifier for the user. This helps the system know which user the tokens belong to.
    val userId: String,
)

