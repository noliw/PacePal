package com.nolawiworkineh.pacepal


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.nolawiworkineh.core.presentation.designsystem.PacePalTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    // **ViewModel Injection**: Retrieves an instance of MainViewModel using Koin dependency injection.
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // **Install Splash Screen**: Sets up the Android Splash Screen API to show a splash screen when the app starts.
        installSplashScreen().apply {
            // **Keep Splash Screen Until Auth Check Completes**: The splash screen stays visible until the auth check is done.
            setKeepOnScreenCondition {
                viewModel.state.isCheckingAuth  // The splash screen stays as long as this is true.
            }
        }

        // **Set Up the UI Content**: Defines the main UI content.
        setContent {
            PacePalTheme {
                // **Surface**: Sets up the surface with the background color from the theme.
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // **After Auth Check**: Only proceed with the app’s navigation if the auth check is complete.
                    if(!viewModel.state.isCheckingAuth) {
                        // **Create NavController**: Initializes the navigation controller.
                        val navController = rememberNavController()

                        // **Navigation Root**: Passes the navigation controller and login status to the navigation system.
                        NavigationRoot(
                            navController = navController,
                            isLoggedIn = viewModel.state.isLoggedIn
                        )
                    }
                }
            }
        }
    }
}


