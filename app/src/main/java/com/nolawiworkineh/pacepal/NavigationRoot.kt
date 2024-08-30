package com.nolawiworkineh.pacepal

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nolawiworkineh.auth.presentation.intro.IntroScreenRoot
import com.nolawiworkineh.auth.presentation.register.RegisterScreenRoot

// **NavigationRoot Function**: This function is the main setup for moving between screens in the app.
@Composable
fun NavigationRoot(
    // **NavHostController**: Think of this as the "manager" that controls where the app should go next when a user interacts with it.
    navController: NavHostController,
) {
    NavHost(
        // **navController**: This is the "manager" that will actually handle the navigation between screens.
        navController = navController,
        // **startDestination**: This tells the app which screen to show first when it opens.
        // Here, it starts with the "auth" section, which deals with things like login and registration.
        startDestination = "auth"
    ) {
        // **authGraph**: This calls another function that sets up all the screens related to authentication,
        // like intro, login, and registration.
        authGraph(navController)
    }
}

// **authGraph Function**: This function organizes all the routes (or paths) related to the authentication part of the app.
private fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation(
        // **startDestination**: Inside the "auth" section, the first screen the user sees is the "intro" screen.
        startDestination = "intro",
        // **route**: This is like the address or name of this section, making it easier to refer to this group of screens later.
        route = "auth"
    ) {
        // **composable**: This defines a screen in the app. Here, it says, "This part is the 'intro' screen."
        composable(route = "intro") {
            IntroScreenRoot(
                onSignUpClick = {
                    // **navigate("register")**: When the user clicks "Sign Up," this tells the app to go to the registration screen.
                    navController.navigate("register")
                },
                onSignInClick = {
                    // **navigate("login")**: When the user clicks "Sign In," this tells the app to go to the login screen.
                    navController.navigate("login")
                }
            )
        }
        // **composable**: This is another screen, this time for registration.
        composable(route = "register") {
            RegisterScreenRoot(
                onSignInClick = {
                    navController.navigate("login") {
                        // **popUpTo("register")**: This removes the registration screen from the back stack (the history of visited screens)
                        // so the user can’t go back to it after they’ve moved on.
                        popUpTo("register") {
                            // **inclusive = true**: This means that the register screen itself will be removed from the history.
                            inclusive = true
                            // **saveState = true**: This saves the state of the screen, so if the user comes back,
                            // they can pick up where they left off.
                            saveState = true
                        }
                        // **restoreState = true**: This restores the state of the login screen, keeping any previous data the user entered.
                        restoreState = true
                    }
                },
                onSuccessfulRegistration = {
                    // **navigate("login")**: After successfully registering, this takes the user to the login screen.
                    navController.navigate("login")
                }
            )
        }
    }
}


