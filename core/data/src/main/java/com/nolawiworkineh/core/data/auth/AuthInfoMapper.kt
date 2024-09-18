package com.nolawiworkineh.core.data.auth

import com.nolawiworkineh.core.domain.AuthInfo.AuthInfo

// **toAuthInfoSerializable**: Converts an in-memory AuthInfo object to its serializable version (AuthInfoSerializable).
fun AuthInfo.toAuthInfoSerializable(): AuthInfoSerializable {
    return AuthInfoSerializable(
        accessToken = accessToken,  // Pass the access token
        refreshToken = refreshToken,  // Pass the refresh token
        userId = userId  // Pass the user ID
    )
}

// **toAuthInfo**: Converts a serializable AuthInfoSerializable object back to its in-memory version (AuthInfo).
fun AuthInfoSerializable.toAuthInfo(): AuthInfo {
    return AuthInfo(
        accessToken = accessToken,  // Pass the access token
        refreshToken = refreshToken,  // Pass the refresh token
        userId = userId  // Pass the user ID
    )
}
