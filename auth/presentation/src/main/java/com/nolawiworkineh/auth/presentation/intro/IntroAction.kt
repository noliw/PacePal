package com.nolawiworkineh.auth.presentation.intro

//  This is the core interface that will encapsulate all user actions on the Intro screen.
sealed interface IntroAction {

    // This represents the action triggered when the user clicks the "Sign In" button.
    data object OnSignInClick : IntroAction

    // This represents the action triggered when the user clicks the "Sign Up" button.
    data object OnSignUpClick : IntroAction
}
