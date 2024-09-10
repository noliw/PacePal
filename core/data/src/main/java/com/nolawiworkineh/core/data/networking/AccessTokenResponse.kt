package com.nolawiworkineh.core.data.networking

import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenResponse (
    val accessToken: String,  // The new access token provided by the server
    val userId: String,  // The user ID for which the token is issued
    val expirationTimestamp: String  // The expiration time of the new access token
)
