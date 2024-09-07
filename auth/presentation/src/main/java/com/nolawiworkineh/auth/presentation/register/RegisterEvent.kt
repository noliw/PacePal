package com.nolawiworkineh.auth.presentation.register

import com.nolawiworkineh.presentation.ui.UiText

// **sealed interface RegisterEvent**: Defines all possible events related to registration.
// Sealed interfaces restrict the possible types of events to only those defined in this file.
sealed interface RegisterEvent {

    // **RegistrationSuccess Event**: This event represents a successful registration.
    // It doesn't carry any additional data, just signifies that registration was successful.
    data object RegistrationSuccess : RegisterEvent

    // **Error Event**: This event represents a failed registration.
    // It carries a message (wrapped in a UiText) that explains what went wrong during registration.
    data class Error(val error: UiText) : RegisterEvent
}

