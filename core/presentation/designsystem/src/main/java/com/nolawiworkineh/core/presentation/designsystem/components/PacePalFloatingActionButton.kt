package com.nolawiworkineh.core.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nolawiworkineh.core.presentation.designsystem.PacePalTheme
import com.nolawiworkineh.core.presentation.designsystem.RunIcon


@Composable
fun PacePalFloatingActionButton(
    // **icon**: The icon displayed in the center of the floating button (e.g., a start or add icon).
    icon: ImageVector,
    // **onClick**: The function executed when the button is clicked. This is where the action happens (e.g., starting a run).
    onClick: () -> Unit,
    // **modifier**: Allows the caller to pass layout or styling modifiers to customize the FAB's appearance or position.
    modifier: Modifier = Modifier,
    // **contentDescription**: Describes the icon for accessibility, allowing screen readers to understand the button's purpose.
    contentDescription: String? = null,
    // **iconSize**: Defines the size of the icon inside the button, customizable to match design needs.
    iconSize: Dp = 25.dp
) {
    // **Outer Box**: Creates a circular background for the FAB with a semi-transparent primary color.
    Box(
        modifier = Modifier
            .size(75.dp)  // Sets the size of the outer circle to 75dp.
            .clip(CircleShape)  // Clips the Box to a circle.
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))  // Semi-transparent background color.
            .clickable(onClick = onClick),  // Makes the FAB clickable.
        contentAlignment = Alignment.Center  // Aligns the content (icon) to the center of the FAB.
    ) {
        // **Inner Box**: The inner circle of the FAB, which has a solid primary color background.
        Box(
            modifier = Modifier
                .size(50.dp)  // Sets the size of the inner circle to 50dp.
                .clip(CircleShape)  // Clips the inner Box to a circle.
                .background(MaterialTheme.colorScheme.primary)  // Solid primary background color.
                .padding(12.dp),  // Adds padding to the inner circle, so the icon is not touching the edges.
            contentAlignment = Alignment.Center  // Aligns the icon to the center of the inner circle.
        ) {
            // **Icon**: The icon displayed in the center of the FAB.
            Icon(
                imageVector = icon,  // The icon passed to the function (e.g., start or add icon).
                contentDescription = contentDescription,  // Describes the icon for accessibility (screen readers).
                tint = MaterialTheme.colorScheme.onPrimary,  // Sets the color of the icon to the theme's onPrimary color.
                modifier = Modifier.size(iconSize)  // Sets the size of the icon, defaulting to 25dp.
            )
        }
    }
}


@Preview
@Composable
fun PacePalFloatingActionButtonPreview() {
    PacePalTheme {
        PacePalFloatingActionButton(
            icon = RunIcon,
            onClick = {},
            contentDescription = null,
        )
    }
}