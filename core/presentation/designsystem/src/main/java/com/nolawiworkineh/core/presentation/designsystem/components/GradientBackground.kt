package com.nolawiworkineh.core.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nolawiworkineh.core.presentation.designsystem.PacePalTheme

//  Applies a radial gradient background to a screen.
@Composable
fun GradientBackground(
    modifier: Modifier = Modifier,
    hasToolbar: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    //  Accesses screen dimensions and density.
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    //  Converts the screen width from dp to pixels.
    val screenWidthPx = with(density) {
        configuration.screenWidthDp.dp.roundToPx()
    }

    // Finds the smaller of the screen's width or height.
    val smallDimension = minOf(
        configuration.screenWidthDp.dp,
        configuration.screenHeightDp.dp
    )
    //  Converts the smaller dimension from dp to pixels.
    val smallDimensionPx = with(density) {
        smallDimension.roundToPx()
    }

    //  Retrieves the primary color from the theme.
    val primaryColor = MaterialTheme.colorScheme.primary

    //  Sets the background color of the entire screen.
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // **Inner container with gradient effect**: Applies a radial gradient as the background.
        Box(
            modifier = modifier
                .fillMaxSize()
                .then(
                    // **Optional blur effect**: Applies a blur effect based on the screen size.
//                    if (isAtLeastAndroid12) {
                    Modifier.blur(smallDimension / 3f)
                )
                // **Radial gradient background**: Creates a radial gradient centered on the screen.
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            primaryColor,
                            MaterialTheme.colorScheme.background
                        ),
                        center = Offset(
                            x = screenWidthPx / 2f,
                            y = -100f
                        ),
                        radius = smallDimensionPx / 1.5f
                    )
                )
        )
        // **Column to hold content**: Places the screen content inside the gradient background.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .then(
                    // **Adjusts for system bars if no toolbar**: Adds padding to avoid system bars.
                    if (hasToolbar) {
                        Modifier
                    } else {
                        Modifier.systemBarsPadding()
                    }
                )
        ) {
            content()
        }
    }
}


@Preview
@Composable
private fun GradientBackgroundPreview() {
    PacePalTheme {
        GradientBackground(
            modifier = Modifier.fillMaxSize()
        ) {

        }
    }
}