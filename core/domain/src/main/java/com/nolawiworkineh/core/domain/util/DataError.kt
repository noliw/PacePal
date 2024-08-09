package com.nolawiworkineh.core.domain.util

// This sealed interface defines specific errors related to data operations.
sealed interface DataError : Error {

    // This enum defines various network-related errors that can occur during data operations.
    enum class Network : DataError {
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

    // This enum defines errors related to local storage issues.
    enum class Local : DataError {
        DISK_FULL
    }
}
