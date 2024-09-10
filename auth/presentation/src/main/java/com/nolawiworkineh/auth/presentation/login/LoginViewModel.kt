@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")
@file:OptIn(ExperimentalFoundationApi::class)

package com.nolawiworkineh.auth.presentation.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.nolawiworkineh.auth.domain.AuthRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class LoginViewModel(
    // The repository that handles the authentication process (network requests).
    private val authRepository: AuthRepository,
): ViewModel() {

    // Holds the current state of the login screen (email, password, validation, loading state).
    var state by mutableStateOf(LoginState())
        private set  // Ensures the state can only be modified within the ViewModel.

    // **eventChannel**: A channel that sends one-time events like success or errors back to the UI.
    private val eventChannel = Channel<LoginEvent>()
    // **events**: This exposes the eventChannel as a Flow, which the UI listens to for events like success or error.
    val events = eventChannel.receiveAsFlow()

    // Processes the user actions or events triggered on the login screen.
    fun onAction(action: LoginAction){
        // Action handling logic will go here.
    }
}
