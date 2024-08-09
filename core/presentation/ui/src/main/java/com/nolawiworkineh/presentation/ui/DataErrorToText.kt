package com.nolawiworkineh.presentation.ui

import com.nolawiworkineh.core.domain.util.DataError

fun DataError.toUiText(): UiText {
    return when (this) {
        DataError.Local.DISK_FULL -> UiText.StringResource(
            R.string.error_disk_full
        )
        DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(
            R.string.error_request_timeout
        )
        DataError.Network.TOO_MANY_REQUESTS -> UiText.StringResource(
            R.string.error_too_many_requests
        )
        DataError.Network.PAYLOAD_TOO_LARGE -> UiText.StringResource(
            R.string.error_payload_too_large
        )
        DataError.Network.NO_INTERNET_CONNECTION -> UiText.StringResource(
            R.string.error_no_internet_connection
        )
        DataError.Network.SERVER_ERROR -> UiText.StringResource(
            R.string.error_server_error
        )
        DataError.Network.SERIALIZATION_ERROR -> UiText.StringResource(
            R.string.error_serialization_error
        )
        else -> UiText.StringResource(
            R.string.error_unknown
        )
    }
}