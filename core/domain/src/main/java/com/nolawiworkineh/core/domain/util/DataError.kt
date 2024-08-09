package com.nolawiworkineh.core.domain.util

// Extends the Error interface and categorizes specific types of data-related errors.
sealed interface DataError : Error {

    // A group of constants representing different network-related errors.
    enum class Network : DataError {
        // Enum constants for various network errors.
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

    // A group of constants representing different local storage-related errors.
    enum class Local : DataError {
        // Enum constant for a full disk error.
        DISK_FULL
    }
}

