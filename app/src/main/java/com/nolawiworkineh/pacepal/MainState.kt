package com.nolawiworkineh.pacepal

// stores the state current state of the app.
data class MainState(
    val isLoggedIn: Boolean = false,  // Tracks if the user is logged in or not.
    val isCheckingAuth: Boolean = false,  // Tracks if the app is currently checking authentication status.
)

