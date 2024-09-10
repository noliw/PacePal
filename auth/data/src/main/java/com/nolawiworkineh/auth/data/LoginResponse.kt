package com.nolawiworkineh.auth.data

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    // **Access Token**: The token used for authenticating API requests.
    val accessToken: String,

    // **Refresh Token**: The token used to refresh the access token when it expires.
    val refreshToken: String,

    // **Access Token Expiration Timestamp**: A timestamp indicating when the access token will expire.
    val accessTokenExpirationTimestamp: Long,

    // **User ID**: The unique identifier of the authenticated user.
    val userId: String
)