package com.nolawiworkineh.pacepal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nolawiworkineh.core.domain.SessionStorage
import kotlinx.coroutines.launch

// thia class will have the logic to check if we are already authenticated
class MainViewModel(
    // This is where we store and retrieve the login info (like the user’s token).
    private val sessionStorage: SessionStorage
) : ViewModel() {

    // This keeps track of whether the user is logged in and whether we’re checking that info.
    var state by mutableStateOf(MainState())
        private set  // Only this ViewModel can change the state.

    //
    init {
        // We're starting a background task to check if the user is already logged in.
        viewModelScope.launch {
            // While we're checking, we set the state to show that the check is in progress.
            state = state.copy(isCheckingAuth = true)

            // Here we check if we have the user's login info stored. If we do, they're logged in.
            state = state.copy(
                isLoggedIn = sessionStorage.get() != null
            )

            // Now that we’re done checking, we tell the app we're finished.
            state = state.copy(isCheckingAuth = false)
        }
    }
}
