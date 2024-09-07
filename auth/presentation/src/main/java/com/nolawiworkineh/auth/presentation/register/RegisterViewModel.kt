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
import com.nolawiworkineh.auth.domain.AuthRepository
import com.nolawiworkineh.auth.domain.UserDataValidator
import com.nolawiworkineh.auth.presentation.R
import com.nolawiworkineh.core.domain.util.DataError
import com.nolawiworkineh.core.domain.util.Result
import com.nolawiworkineh.presentation.ui.UiText
import com.nolawiworkineh.presentation.ui.toUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

// **RegisterViewModel Class**: Manages the state and actions for the registration screen.
// **RegisterViewModel Class**: Manages the state and logic for the registration screen.
class RegisterViewModel(
    private val userDataValidator: UserDataValidator, // **Dependency Injection**: The UserDataValidator is injected into the ViewModel.
    private val repository: AuthRepository, // **Dependency Injection**: The AuthRepository is injected into the ViewModel.
) : ViewModel() { // **Inherits from ViewModel**: Allows the class to survive configuration changes and manage UI-related data.


    // **State Property**: Holds the current state of the registration screen. (email, password, etc.)
    var state by mutableStateOf(RegisterState()) // **mutableStateOf**: A state holder that allows reactive updates to the UI.
        // the state can only be modified from inside the ViewModel (private setter).
        private set // **Private Setter**: Prevents external modification of the state directly.

    // **eventChannel**: A channel that sends one-time events like success or errors back to the UI.
    private val eventChannel = Channel<RegisterEvent>()
    // **events**: This exposes the eventChannel as a Flow, which the UI listens to for events like success or error.
    val events = eventChannel.receiveAsFlow()

    init { // **init Block**: Executes when the ViewModel is first created.
        // **Email Validation Flow**: Observes changes in the email text and updates the validation status.
        state.email.textAsFlow()
            .onEach { email -> // **onEach**: Executes the given block of code for each new value emitted by the flow.
                val isEmailValid = userDataValidator.isValidEmail(email.toString()) // **isValidEmail**: Validates the email.
                state = state.copy( // **state.copy()**: Creates a new state object with updated properties.
                    isEmailValid = isEmailValid, // **isValidEmail**: Validates the email and updates the state.
                    // **canRegister**: Determines if registration is possible based on email and password validation.
                    canRegister = isEmailValid && state.passwordValidationState.isValidPassword && !state.isRegistering
                )
            }
            .launchIn(viewModelScope) // **launchIn**: Launches the flow in the ViewModel's coroutine scope.

        // **Password Validation Flow**: Observes changes in the password text and updates the validation status.
        state.password.textAsFlow()
            .onEach { password -> // **onEach**: Executes the given block of code for each new value emitted by the flow.
                val passwordValidationState = userDataValidator.validatePassword(password.toString()) // **validatePassword**: Validates the password.
                state = state.copy( // **state.copy()**: Creates a new state object with updated properties.
                    // **validatePassword**: Validates the password and updates the state.
                    passwordValidationState = passwordValidationState,
                    // **canRegister**: Determines if registration is possible based on email and password validation.
                    canRegister = state.isEmailValid && passwordValidationState.isValidPassword && !state.isRegistering
                )
            }
            .launchIn(viewModelScope) // **launchIn**: Launches the flow in the ViewModel's coroutine scope.
    }

    // **onAction function**: Handles all actions that can be triggered from the UI.
    fun onAction(action: RegisterAction) {
        when(action) {
            // **OnRegisterClick**: Calls the register function when the user clicks the register button.
            RegisterAction.OnRegisterClick -> register()

            // **OnTogglePasswordVisibilityClick**: Toggles password visibility when the user clicks to show/hide password.
            RegisterAction.OnTogglePasswordVisibilityClick -> {
                state = state.copy(
                    isPasswordVisible = !state.isPasswordVisible  // Toggles the password visibility state.
                )
            }
            // Default case for other actions that aren’t handled here.
            else -> Unit
        }
    }

    // **register function**: Handles the registration process when the user clicks the register button.
    private fun register() {
        viewModelScope.launch {
            // **state = state.copy(isRegistering = true)**: Sets the loading state to true to show the loading indicator.
            state = state.copy(isRegistering = true)

            // **result**: Calls the repository's register function to attempt user registration, passing in email and password.
            val result = repository.register(
                email = state.email.text.toString().trim(),  // Sends the user's email, trimming any excess spaces.
                password = state.password.text.toString()  // Sends the user's password.
            )

            // **state = state.copy(isRegistering = false)**: After the registration attempt, stop the loading indicator.
            state = state.copy(isRegistering = false)

            // **When block**: Handles the result of the registration attempt (success or error).
            when(result) {
                // **Error case**: Handles any errors returned from the registration process.
                is Result.Error -> {
                    // **If the error is a conflict (e.g., email already exists)**:
                    if(result.error == DataError.Network.CONFLICT) {
                        eventChannel.send(RegisterEvent.Error(
                            UiText.StringResource(R.string.error_email_exists)  // Sends an error event with a message.
                        ))
                    } else {
                        // **For all other network errors**: Sends a general error event with the appropriate message.
                        eventChannel.send(RegisterEvent.Error(result.error.toUiText()))
                    }
                }

                // **Success case**: If the registration was successful, send a success event.
                is Result.Success -> {
                    eventChannel.send(RegisterEvent.RegistrationSuccess)  // Sends a success event.
                }
            }
        }
    }
}