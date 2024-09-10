@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")

package com.nolawiworkineh.auth.presentation.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nolawiworkineh.auth.domain.AuthRepository

class LoginViewModel(
    // The repository that handles the authentication process (network requests).
    private val authRepository: AuthRepository,
): ViewModel() {

    // Holds the current state of the login screen (email, password, validation, loading state).
    var state = mutableStateOf(LoginState())
        private set  // Ensures the state can only be modified within the ViewModel.

    // Processes the user actions or events triggered on the login screen.
    fun onAction(event: LoginEvent){
        // Action handling logic will go here.
    }
}
