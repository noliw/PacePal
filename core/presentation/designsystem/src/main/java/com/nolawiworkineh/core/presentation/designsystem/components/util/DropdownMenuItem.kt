package com.nolawiworkineh.core.presentation.designsystem.components.util

import androidx.compose.ui.graphics.vector.ImageVector

// **DropdownMenuItem**: A data class representing a single item in a dropdown menu.
data class DropdownMenuItem(
    // **icon**: An ImageVector representing the icon for the dropdown menu item (e.g., a settings or logout icon).
    val icon: ImageVector,

    // **title**: A string representing the text that describes the action or option in the dropdown (e.g., "Logout", "Settings").
    val title: String
)

