package com.nolawiworkineh.pacepal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nolawiworkineh.core.domain.SessionStorage
import kotlinx.coroutines.launch

//let's go to the app module and have a view model that is bound to the activity.
       // in here we will have the logic to check if we are already authenticated

class MainViewModel (
    private val sessionStorage : SessionStorage
) : ViewModel() {

    var state by mutableStateOf(MainState())
    private set

    init {
        viewModelScope.launch {
            state = state.copy(isCheckingAuth = true)
            state = state.copy(
                isLoggedIn = sessionStorage.get() != null,

            )
            state = state.copy(isCheckingAuth = false)
        }
    }
}