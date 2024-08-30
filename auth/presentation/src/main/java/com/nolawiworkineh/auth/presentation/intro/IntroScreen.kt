package com.nolawiworkineh.auth.presentation.intro

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nolawiworkineh.auth.presentation.R
import com.nolawiworkineh.core.presentation.designsystem.LogoIcon
import com.nolawiworkineh.core.presentation.designsystem.PacePalLightGray
import com.nolawiworkineh.core.presentation.designsystem.PacePalTheme
import com.nolawiworkineh.core.presentation.designsystem.components.GradientBackground
import com.nolawiworkineh.core.presentation.designsystem.components.PacePalActionButton
import com.nolawiworkineh.core.presentation.designsystem.components.PacePalOutlinedActionButton

// **IntroScreenRoot Composable Function**: A root composable that wraps the IntroScreen and handles navigation.
@Composable
fun IntroScreenRoot(
    onSignUpClick: () -> Unit, // **Callback for Sign Up Action**: Triggers navigation or other logic when Sign Up is clicked.
    onSignInClick: () -> Unit // **Callback for Sign In Action**: Triggers navigation or other logic when Sign In is clicked.
) {
    // **IntroScreen Composable**: The actual screen UI, with its actions handled by the root.
    IntroScreen(
        onAction = { action ->
            when (action) {
                IntroAction.OnSignUpClick -> onSignUpClick() // **Handle Sign Up**: Calls the onSignUp callback when Sign Up is clicked.
                IntroAction.OnSignInClick -> onSignInClick() // **Handle Sign In**: Calls the onSignIn callback when Sign In is clicked.
            }
        }
    )
}

// **IntroScreen Composable Function**: Displays the Intro screen UI and handles user actions.
@Composable
fun IntroScreen(
    onAction: (IntroAction) -> Unit // **Action Callback**: Triggers actions based on user interaction.
) {
    // **GradientBackground**: Applies a gradient background to the screen.
    GradientBackground {
        // **Logo Section**: Displays the app's logo in the center of the screen.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), // **Flexible Height**: The box takes up flexible height based on its content.
            contentAlignment = Alignment.Center // **Center Content**: The logo is centered within the box.
        ) {
            PacePaleVerticalLogo() // **App Logo**: A vertical logo displayed in the center of the screen.
        }

        // **Action Buttons Section**: Displays the Sign In and Sign Up buttons.
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp) // **Padding**: Adds padding around the buttons for better spacing.
                .padding(bottom = 48.dp), // **Bottom Padding**: Adds extra space at the bottom for aesthetics.
            horizontalAlignment = Alignment.CenterHorizontally // **Center Align**: The buttons are centered horizontally.
        ) {
            // **Welcome Text**: A welcoming message at the top of the button section.
            Text(
                text = stringResource(id = R.string.welcome_to_pacepal), // **String Resource**: The text is fetched from string resources.
                color = MaterialTheme.colorScheme.onBackground, // **Text Color**: The color matches the theme's on-background color.
                fontSize = 20.sp, // **Font Size**: Sets the size of the welcome text.
                style = MaterialTheme.typography.headlineMedium, // **Text Style**: Uses the headlineMedium typography style.
            )

            Spacer(modifier = Modifier.height(8.dp)) // **Spacer**: Adds space between the welcome text and description.

            // **Description Text**: A brief description of the app's purpose.
            Text(
                text = stringResource(id = R.string.pacepal_description), // **String Resource**: The text is fetched from string resources.
                style = MaterialTheme.typography.bodySmall, // **Text Style**: Uses the bodySmall typography style.
                textAlign = TextAlign.Center, // **Center Align**: The text is centered horizontally.
                color = PacePalLightGray // **Text Color**: Uses a light gray color for the description text.
            )

            Spacer(modifier = Modifier.height(32.dp)) // **Spacer**: Adds space between the description and buttons.

            // **Sign In Button**: An outlined button for signing in.
            PacePalOutlinedActionButton(
                text = stringResource(id = R.string.sign_in), // **Button Label**: The text is fetched from string resources.
                isLoading = false, // **Loading State**: No loading indicator is shown by default.
                onClick = {
                    onAction(IntroAction.OnSignInClick) // **Action Trigger**: Triggers the Sign In action when clicked.
                },
                modifier = Modifier
                    .fillMaxWidth() // **Full-Width**: The button fills the entire width of the parent.
            )

            Spacer(modifier = Modifier.height(16.dp)) // **Spacer**: Adds space between the Sign In and Sign Up buttons.

            // **Sign Up Button**: A filled button for signing up.
            PacePalActionButton(
                text = stringResource(id = R.string.sign_up), // **Button Label**: The text is fetched from string resources.
                isLoading = false, // **Loading State**: No loading indicator is shown by default.
                modifier = Modifier.fillMaxWidth(), // **Full-Width**: The button fills the entire width of the parent.
                onClick = {
                    onAction(IntroAction.OnSignUpClick) // **Action Trigger**: Triggers the Sign Up action when clicked.
                }
            )
        }
    }
}

// **PacePaleVerticalLogo Composable Function**: Displays the app's logo in a vertical arrangement.
@Composable
private fun PacePaleVerticalLogo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally, // **Center Align**: The logo and text are centered horizontally.
        verticalArrangement = Arrangement.Center // **Center Vertically**: The content is centered vertically within the column.
    ) {
        // **Logo Icon**: Displays the app's logo icon.
        Icon(
            imageVector = LogoIcon, // **Icon Image**: The icon image is fetched from vector resources.
            contentDescription = "Logo", // **Content Description**: Provides an accessible description of the logo.
            tint = MaterialTheme.colorScheme.onBackground, // **Icon Color**: The icon color matches the theme's on-background color.
            modifier = Modifier.size(100.dp) // **Icon Size**: Sets the size of the icon.
        )
        Spacer(modifier = Modifier.height(12.dp)) // **Spacer**: Adds space between the icon and text.
        // **App Name Text**: Displays the app's name below the logo.
        Text(
            text = stringResource(id = R.string.pacepal), // **String Resource**: The text is fetched from string resources.
            fontSize = 24.sp, // **Font Size**: Sets the size of the text.
            fontWeight = FontWeight.Medium, // **Font Weight**: Sets the text weight to medium.
            color = MaterialTheme.colorScheme.onBackground // **Text Color**: The text color matches the theme's on-background color.
        )
    }
}


@Preview
@Composable
private fun IntroScreenPreview() {
    PacePalTheme {
        IntroScreen(onAction = {})
    }

}