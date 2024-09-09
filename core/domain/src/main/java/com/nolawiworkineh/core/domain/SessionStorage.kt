package com.nolawiworkineh.core.domain

// **SessionStorage**: An interface that defines methods for getting and setting authentication info (AuthInfo).
interface SessionStorage {
    // **get**: Retrieves the current AuthInfo (tokens and user ID) if it exists. Returns null if no session is active.
    suspend fun get(): AuthInfo?

    // **set**: Stores the provided AuthInfo (access token, refresh token, user ID).
    suspend fun set(info: AuthInfo?)
}
