package com.nolawiworkineh.core.data.networking

import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenResponse (
    val accessToken: String,
    val userId: String,
    val expirationTimestamp: String,
)