@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")
@file:OptIn(ExperimentalFoundationApi::class)

package com.nolawiworkineh.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

// **RegisterViewModel Class**: Manages the state and actions for the registration screen.
class RegisterViewModel: ViewModel() {

    // **State Property**: Holds the current state of the registration screen.
    var state by mutableStateOf(RegisterState())
        private set // **Private Setter**: Prevents external modification of the state directly.

    // **onAction Function**: Handles user actions on the registration screen.
    fun onAction(action: RegisterAction) {

    }
}
