@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")
@file:OptIn(ExperimentalFoundationApi::class)

package com.nolawiworkineh.auth.presentation.login

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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    // The repository that handles the authentication process (network requests).
    private val authRepository: AuthRepository,
    // The validator that validates user input (e.g., email and password)
    // in order to enable and disable our login button.
    private val userDataValidator: UserDataValidator
): ViewModel() {

    // 1. STATE MANAGEMENT
    // Holds the current state of the login screen (email, password, validation, loading state).
    var state by mutableStateOf(LoginState())
        private set  // Ensures the state can only be modified within the ViewModel.

    // 2. EVENT HANDLING
    // **eventChannel**: A channel that sends one-time events like success or errors back to the UI.
    private val eventChannel = Channel<LoginEvent>()
    // **events**: This exposes the eventChannel as a Flow,
    // which the UI listens to for events like success or error.
    val events = eventChannel.receiveAsFlow()

    // 3. INITIALIZATION
    // **init Block**: Executes when the ViewModel is first created.
    init {
        // Combine two flows: one for the email input and one for the password input.
        combine(
            // Convert the email text field to a flow that emits values every time the email changes.
            state.email.textAsFlow(),

            // Convert the password text field to a flow that emits values every time the password changes.
            state.password.textAsFlow()
        ) { email, password ->
            // Every time either the email or password changes, update the state with new conditions.
            state = state.copy(
                // Check if the email is valid AND the password is not empty.
                canLogin = userDataValidator.isValidEmail(
                    email = email.toString().trim()  // Validate the trimmed email string.
                ) && password.isNotEmpty()  // Ensure the password is not empty.
            )
        }.launchIn(viewModelScope)  // Launch the flow in the ViewModel’s coroutine scope.
    }

    // Processes the user actions or events triggered on the login screen.
    fun onAction(action: LoginAction){
        when(action) {
            LoginAction.onLoginClick -> login()
            LoginAction.onTogglePasswordVisibility -> {
                state = state.copy(
                    isPasswordVisible = !state.isPasswordVisible
                )
            }
            // everything else is a pure navigation action so we don't need to do anything here
            else -> Unit
        }
    }

    private fun login() {
        viewModelScope.launch {
            state = state.copy(isLoginInProgress = true)
            val result = authRepository.login(
                email = state.email.text.toString().trim(),
                password = state.password.text.toString()
            )
            state = state.copy(isLoginInProgress = false)

            when(result) {
                // **Error case**: Handles any errors returned from the registration process.
                is Result.Error -> {
                    // **If the error is a conflict (e.g., email already exists)**:
                    if(result.error == DataError.Network.UNAUTHORIZED) {
                        eventChannel.send(
                            LoginEvent.Error(
                            UiText.StringResource(R.string.error_email_exists)  // Sends an error event with a message.
                        ))
                    } else {
                        // **For all other network errors**: Sends a general error event with the appropriate message.
                        eventChannel.send(LoginEvent.Error(result.error.toUiText()))
                    }
                }

                // **Success case**: If the registration was successful, send a success event.
                is Result.Success -> {
                    eventChannel.send(LoginEvent.LoginSuccess)  // Sends a success event.
                }
            }
        }
    }
}



