package com.nolawiworkineh.pacepal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nolawiworkineh.core.domain.SessionStorage
import kotlinx.coroutines.launch

// this class will have the logic to check if we are already authenticated
class MainViewModel (
    // **SessionStorage Dependency**: Provides access to session storage, where the authentication token is stored.
    private val sessionStorage: SessionStorage
) : ViewModel() {

    // **State Management**: Holds the state of the app (whether the user is logged in, checking authentication, etc.).
    var state by mutableStateOf(MainState())
        private set  // **Private Setter**: Ensures the state can only be modified within the ViewModel.

    init {
        // **Coroutine Scope**: Executes the authentication check asynchronously.
        viewModelScope.launch {
            // **Set Checking State**: Update the state to indicate that the app is checking authentication.
            state = state.copy(isCheckingAuth = true)

            // **Check Authentication**: Check if there's an existing session (user is logged in).
            state = state.copy(
                isLoggedIn = sessionStorage.get() != null,  // If a session exists, the user is logged in.
            )

            // **Finished Checking**: Set the state to indicate that the authentication check is complete.
            state = state.copy(isCheckingAuth = false)
        }
    }
}
