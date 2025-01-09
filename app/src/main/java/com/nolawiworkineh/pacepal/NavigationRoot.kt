package com.nolawiworkineh.pacepal

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.nolawiworkineh.auth.presentation.intro.IntroScreenRoot
import com.nolawiworkineh.auth.presentation.login.LoginScreenRoot
import com.nolawiworkineh.auth.presentation.register.RegisterScreenRoot
import com.nolawiworkineh.run.presentation.active_run.ActiveRunScreenRoot
import com.nolawiworkineh.run.presentation.active_run.service.ActiveRunService
import com.nolawiworkineh.run.presentation.run_overview.RunOverviewRoot

// **NavigationRoot Function**: This function is the main setup for moving between screens in the app.
@Composable
fun NavigationRoot(
    // **NavHostController**: Think of this as the "manager" that controls where the app should go next when a user interacts with it.
    navController: NavHostController,
    // **isLoggedIn**: A flag that indicates whether the user is logged in or not.
    isLoggedIn: Boolean
) {
    NavHost(
        // **navController**: This is the "manager" that will actually handle the navigation between screens.
        navController = navController,
        // **startDestination**: This tells the app which screen to show first when it opens.
        // Here, it starts with the "auth" section, which deals with things like login and registration.
        startDestination = if (isLoggedIn) "runFeature" else "authFeature"
    ) {
        // **authGraph**: This calls another function that sets up all the screens related to authentication,
        // like intro, login, and registration.
        authGraph(navController)
        runGraph(navController)
    }
}

// **authGraph Function**: This function organizes all the routes (or paths) related to the authentication part of the app.
private fun NavGraphBuilder.authGraph(navController: NavHostController)     {
    navigation(
        // **startDestination**: Inside the "auth" section, the first screen the user sees is the "intro" screen.
        startDestination = "introScreen",
        // **route**: This is like the address or name of this section, making it easier to refer to this group of screens later.
        route = "authFeature"
    ) {
        // **composable**: This defines a screen in the app. Here, it says, "This part is the 'intro' screen."
        composable(route = "introScreen") {
            IntroScreenRoot(
                onSignUpClick = {
                    // **navigate("register")**: When the user clicks "Sign Up," this tells the app to go to the registration screen.
                    navController.navigate("registerScreen")
                },
                onSignInClick = {
                    // **navigate("login")**: When the user clicks "Sign In," this tells the app to go to the login screen.
                    navController.navigate("loginScreen")
                }
            )
        }
        // **composable**: This is another screen, this time for registration.
        composable(route = "registerScreen") {
            RegisterScreenRoot(
                onSignInClick = {
                    navController.navigate("loginScreen") {
                        // **popUpTo("register")**: This removes the registration screen from the back stack (the history of visited screens)
                        // so the user can’t go back to it after they’ve moved on.
                        popUpTo("registerScreen") {
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
                    navController.navigate("loginScreen")
                }

            )
        }
        composable(route = "loginScreen") {
            // **LoginScreenRoot**: Displays the login screen and handles login success and sign-up navigation.
            LoginScreenRoot(
                onLoginSuccess = {
                    // **Navigate to Run Screen on Success**: On successful login, navigate to the "run" screen.
                    navController.navigate("runFeature") {
                        // **popUpTo("auth")**: Removes the "auth" navigation graph from the back stack, preventing back navigation to the login screen.
                        popUpTo("authFeature") {
                            inclusive = true  // **inclusive**: Ensures that "auth" itself is removed from the back stack.
                        }
                    }
                },
                onSignUpClick = {
                    // **Navigate to Register Screen on Sign-Up Click**: On click of the "Sign Up" link, navigate to the "register" screen.
                    navController.navigate("registerScreen") {
                        // **popUpTo("login")**: Removes the "login" screen from the back stack.
                        popUpTo("loginScreen") {
                            inclusive = true  // **inclusive**: Removes the "login" screen itself from the back stack.
                            saveState = true  // **saveState**: Saves the state of the screen so it can be restored if the user navigates back to "login".
                        }
                        restoreState = true  // **restoreState**: Restores the saved state when the user navigates back to "login".
                    }
                }
            )
        }

    }
}


private fun NavGraphBuilder.runGraph(navController: NavHostController) {
    navigation(
        startDestination = "run_overview",
        route = "runFeature"
    ) {
        composable("run_overview") {
            RunOverviewRoot(
                onStartRunClick = {
                    navController.navigate("active_run")
                }
            )
        }
        composable(
            route = "active_run",
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "pacepal://active_run"
                }
            )
        ) {
            val context = LocalContext.current
            ActiveRunScreenRoot(
                onBackClick = {
                    navController.navigateUp()
                },
                onFinish = {
                    navController.navigateUp()
                },
                onServiceToggle = { shouldServiceRun ->

                    if(shouldServiceRun) {
                        context.startService(
                            ActiveRunService.createStartIntent(
                                context = context,
                                activityClass = MainActivity::class.java
                            )
                        )
                    } else {
                        context.startService(
                            ActiveRunService.createStopIntent(context = context,)
                        )
                    }
                }
            )
        }
    }
}

