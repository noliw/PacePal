package com.nolawiworkineh.core.data.networking

import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenRequest (
    val refreshToken: String,  // The refresh token, sent to request a new access token
    val userId: String  // The user ID associated with the refresh token
)
