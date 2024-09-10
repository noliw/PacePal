package com.nolawiworkineh.core.data.auth

import kotlinx.serialization.Serializable

// **AuthInfoSerializable**: A serializable version of the AuthInfo data class for storing or transmitting authentication details.
@Serializable
data class AuthInfoSerializable(
    // **Access Token**: A token used for making authenticated API requests.
    val accessToken: String,

    // **Refresh Token**: A token used to generate a new access token when the current one expires.
    val refreshToken: String,

    // **User ID**: The unique identifier for the user in the backend.
    val userId: String,
)

