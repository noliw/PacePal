package com.nolawiworkineh.core.domain.util

sealed interface DataError: Error {
    enum class Network: DataError {
        REQUEST_TIMEOUT,
        UNAUTHORIZED,
        CONFLICT,
        TOO_MANY_REQUESTS,
        PAYLOAD_TOO_LARGE,
        NO_INTERNET_CONNECTION,
        SERVER_ERROR,
        SERIALIZATION_ERROR,
        UNKNOWN_ERROR
    }

    enum class Local: DataError {
        DISK_FULL
    }
}