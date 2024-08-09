package com.nolawiworkineh.presentation.ui

import com.nolawiworkineh.core.domain.util.DataError

// Converts a DataError to a user-friendly UiText message.
fun DataError.toUiText(): UiText {
    //  Matches the DataError type and returns the corresponding UiText.
    return when (this) {
        // Maps the DISK_FULL error to a specific string resource.
        DataError.Local.DISK_FULL -> UiText.StringResource(
            R.string.error_disk_full
        )
        // Maps the REQUEST_TIMEOUT error to a specific string resource.
        DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(
            R.string.error_request_timeout
        )
        // Maps the TOO_MANY_REQUESTS error to a specific string resource.
        DataError.Network.TOO_MANY_REQUESTS -> UiText.StringResource(
            R.string.error_too_many_requests
        )
        // Maps the PAYLOAD_TOO_LARGE error to a specific string resource.
        DataError.Network.PAYLOAD_TOO_LARGE -> UiText.StringResource(
            R.string.error_payload_too_large
        )
        // Maps the NO_INTERNET_CONNECTION error to a specific string resource.
        DataError.Network.NO_INTERNET_CONNECTION -> UiText.StringResource(
            R.string.error_no_internet_connection
        )
        // Maps the SERVER_ERROR error to a specific string resource.
        DataError.Network.SERVER_ERROR -> UiText.StringResource(
            R.string.error_server_error
        )
        // Maps the SERIALIZATION_ERROR error to a specific string resource.
        DataError.Network.SERIALIZATION_ERROR -> UiText.StringResource(
            R.string.error_serialization_error
        )
        // Catches any other errors and maps them to a generic unknown error string resource.
        else -> UiText.StringResource(
            R.string.error_unknown
        )
    }
}
