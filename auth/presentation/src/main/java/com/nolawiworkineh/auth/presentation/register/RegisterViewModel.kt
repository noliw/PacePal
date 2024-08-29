@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")
@file:OptIn(ExperimentalFoundationApi::class)

package com.nolawiworkineh.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nolawiworkineh.auth.domain.UserDataValidator
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

// **RegisterViewModel Class**: Manages the state and actions for the registration screen.
class RegisterViewModel(
    private val userDataValidator: UserDataValidator
): ViewModel() {

    // **State Property**: Holds the current state of the registration screen.
    var state by mutableStateOf(RegisterState())
        private set // **Private Setter**: Prevents external modification of the state directly.

    init {
        state.email.textAsFlow()
            .onEach { email ->
                state = state.copy(
                    isEmailValid = userDataValidator.isValidEmail(email.toString())
                )
            }
            .launchIn(viewModelScope)

        state.password.textAsFlow()
            .onEach { password ->
                state = state.copy(
                    passwordValidationState = userDataValidator.validatePassword(password.toString())
                )
            }
            .launchIn(viewModelScope)
    }

    // **onAction Function**: Handles user actions on the registration screen.
    fun onAction(action: RegisterAction) {

    }
}
