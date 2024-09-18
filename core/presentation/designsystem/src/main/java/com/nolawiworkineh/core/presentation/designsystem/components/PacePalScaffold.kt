package com.nolawiworkineh.core.presentation.designsystem.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PacePalScaffold(
    // **modifier**: Customizes the layout or styling of the scaffold itself (e.g., padding or background changes).
    modifier: Modifier = Modifier,

    // **withGradient**: Determines whether the background will include a gradient effect. Defaults to true.
    withGradient: Boolean = true,

    // **topAppBar**: A composable function representing the toolbar or top bar of the screen. Defaults to an empty function.
    topAppBar: @Composable () -> Unit = {},

    // **floatingActionButton**: A composable function for the floating action button. Defaults to an empty function.
    floatingActionButton: @Composable () -> Unit = {},

    // **content**: The main content area of the screen. Takes padding values to ensure content doesn’t overlap with UI elements like the top bar.
    content: @Composable (PaddingValues) -> Unit
) {
    // **Scaffold**: The built-in composable function that handles the layout of the top bar, floating action button, and content.
    Scaffold(
        topBar = topAppBar,  // Uses the provided composable for the top app bar (or an empty one if not provided).
        floatingActionButton = floatingActionButton,  // The provided composable for the floating action button.
        floatingActionButtonPosition = FabPosition.Center,  // Places the floating action button at the center of the screen.
        modifier = modifier  // Applies any custom modifiers passed to the scaffold.
    ) { padding ->
        // If the gradient is enabled, apply it as the background for the main content.
        if (withGradient) {
            // **GradientBackground**: A custom composable that applies a gradient effect as the background.
            GradientBackground {
                // Displays the main content of the screen, adjusting for any padding from UI elements like the top bar.
                content(padding)
            }
        } else {
            // If no gradient is required, simply display the content with the padding applied.
            content(padding)
        }
    }
}
