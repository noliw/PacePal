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
// **RegisterViewModel Class**: Manages the state and logic for the registration screen.
class RegisterViewModel(
    private val userDataValidator: UserDataValidator // **Dependency Injection**: The UserDataValidator is injected into the ViewModel.
) : ViewModel() { // **Inherits from ViewModel**: Allows the class to survive configuration changes and manage UI-related data.

    // **State Property**: Holds the current state of the registration screen.
    var state by mutableStateOf(RegisterState()) // **mutableStateOf**: A state holder that allows reactive updates to the UI.
        private set // **Private Setter**: Prevents external modification of the state directly.

    init { // **init Block**: Executes when the ViewModel is first created.
        // **Email Validation Flow**: Observes changes in the email text and updates the validation status.
        state.email.textAsFlow()
            .onEach { email -> // **onEach**: Executes the given block of code for each new value emitted by the flow.
                state = state.copy( // **state.copy()**: Creates a new state object with updated properties.
                    isEmailValid = userDataValidator.isValidEmail(email.toString()) // **isValidEmail**: Validates the email and updates the state.
                )
            }
            .launchIn(viewModelScope) // **launchIn**: Launches the flow in the ViewModel's coroutine scope.

        // **Password Validation Flow**: Observes changes in the password text and updates the validation status.
        state.password.textAsFlow()
            .onEach { password -> // **onEach**: Executes the given block of code for each new value emitted by the flow.
                state = state.copy( // **state.copy()**: Creates a new state object with updated properties.
                    // **validatePassword**: Validates the password and updates the state.
                    passwordValidationState = userDataValidator.validatePassword(password.toString())
                )
            }
            .launchIn(viewModelScope) // **launchIn**: Launches the flow in the ViewModel's coroutine scope.
    }

    // **onAction Function**: Handles user actions on the registration screen.
    fun onAction(action: RegisterAction) {
        // Implementation for handling user actions goes here
    }
}
