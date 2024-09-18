@file:OptIn(ExperimentalMaterial3Api::class)

package com.nolawiworkineh.core.presentation.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nolawiworkineh.core.presentation.designsystem.AnalyticsIcon
import com.nolawiworkineh.core.presentation.designsystem.ArrowLeftIcon
import com.nolawiworkineh.core.presentation.designsystem.LogoIcon
import com.nolawiworkineh.core.presentation.designsystem.PacePalBlue
import com.nolawiworkineh.core.presentation.designsystem.PacePalTheme
import com.nolawiworkineh.core.presentation.designsystem.Poppins
import com.nolawiworkineh.core.presentation.designsystem.R
import com.nolawiworkineh.core.presentation.designsystem.components.util.DropdownMenuItem

@Composable
fun PacePalToolbar(
    // **modifier**: Allows the caller to pass layout or styling modifiers to customize the toolbar's appearance.
    modifier: Modifier = Modifier,

    // **showBackButton**: A flag to determine whether the toolbar should show a back button for navigation.
    showBackButton: Boolean = false,

    // **title**: The text that will be displayed in the center of the toolbar as the title.
    title: String = "",

    // **menuItems**: A list of items to be shown in the toolbar’s dropdown menu, such as settings or logout options.
    menuItems: List<DropdownMenuItem> = emptyList(),

    // **onMenuItemClick**: Callback function to handle what happens when a user clicks a menu item. Each item in the dropdown is identified by its index.
    onMenuItemClick: (Int) -> Unit = {},

    // **onBackClick**: Callback function to handle the back button click. Typically used for navigation (e.g., returning to a previous screen).
    onBackClick: () -> Unit = {},

    // **scrollBehavior**: Defines how the toolbar reacts to scrolling, such as hiding or sticking to the top.
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),

    // **startContent**: Optional composable content that can be placed at the start of the toolbar (e.g., an icon or logo).
    startContent: (@Composable () -> Unit)? = null,
) {
    // **isDropDownOpen**: A flag to track whether the dropdown menu is currently open.
    var isDropDownOpen by rememberSaveable { mutableStateOf(false) }

    // **TopAppBar**: The main toolbar component provided by the Material library.
    TopAppBar(
        title = {
            // **Row**: Aligns the title and optional start content (such as an icon or logo) horizontally.
            Row(
                verticalAlignment = Alignment.CenterVertically // Aligns the content vertically to the center.
            ) {
                // **startContent?.invoke()**: If startContent is provided, it is displayed here, typically an icon or logo.
                startContent?.invoke()

                // **Spacer**: Adds a small space (8dp) between the start content and the title.
                Spacer(modifier = Modifier.width(8.dp))

                // **Text**: Displays the title in a specific style, including font, weight, and color.
                Text(
                    text = title,  // Uses the title provided as a parameter.
                    fontWeight = FontWeight.SemiBold,  // Makes the title text semi-bold.
                    color = MaterialTheme.colorScheme.onBackground,  // Uses the onBackground color from the current theme.
                    fontFamily = Poppins  // Uses the Poppins font for the title.
                )
            }
        },
        // **modifier**: Applies any additional layout or styling passed to the toolbar.
        modifier = modifier,

        // **scrollBehavior**: Defines how the toolbar behaves when the screen is scrolled (e.g., hide or remain visible).
        scrollBehavior = scrollBehavior,

        // **colors**: Sets the color of the toolbar. We set it to transparent to keep it customizable.
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent  // Makes the toolbar background transparent.
        ),

        // **navigationIcon**: If the back button is enabled, it shows the back button on the left side of the toolbar.
        navigationIcon = {
            if (showBackButton) {
                // **IconButton**: Creates a clickable button for the back icon.
                IconButton(onClick = onBackClick) {
                    // **Icon**: Displays the back icon (ArrowLeftIcon) with a label for accessibility.
                    Icon(
                        imageVector = ArrowLeftIcon,  // Arrow left icon for navigation.
                        contentDescription = stringResource(id = R.string.go_back),  // Describes the icon for accessibility.
                        tint = MaterialTheme.colorScheme.onBackground  // The color of the icon, matching the theme.
                    )
                }
            }
        },

        // **actions**: Displays actions like a dropdown menu on the right side of the toolbar.
        actions = {
            if (menuItems.isNotEmpty()) {  // Only show the dropdown if there are menu items.
                // **Box**: Contains the dropdown and the menu button.
                Box {
                    // **DropdownMenu**: The actual dropdown that shows menu items when expanded.
                    DropdownMenu(
                        expanded = isDropDownOpen,  // Determines if the dropdown is visible.
                        onDismissRequest = { isDropDownOpen = false }  // Closes the dropdown when clicked outside.
                    ) {
                        // Loops through the menu items and creates a clickable row for each.
                        menuItems.forEachIndexed { index, item ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,  // Aligns the icon and text in each row.
                                modifier = Modifier
                                    .clickable { onMenuItemClick(index) }  // Handles clicks on each menu item.
                                    .fillMaxWidth()  // Makes the menu item take up the full width of the dropdown.
                                    .padding(16.dp)  // Adds padding around each menu item.
                            ) {
                                // **Icon**: Displays the icon for each menu item.
                                Icon(
                                    imageVector = item.icon,  // Uses the icon from the menu item.
                                    contentDescription = item.title  // Describes the icon for accessibility.
                                )
                                // **Spacer**: Adds space between the icon and text.
                                Spacer(modifier = Modifier.width(8.dp))
                                // **Text**: Displays the title of the menu item.
                                Text(text = item.title)
                            }
                        }
                    }
                    // **IconButton**: The button that opens the dropdown menu.
                    IconButton(onClick = { isDropDownOpen = true }) {
                        // **Icon**: Displays the three-dot menu icon.
                        Icon(
                            imageVector = Icons.Default.MoreVert,  // The vertical three-dot icon for the menu.
                            contentDescription = stringResource(id = R.string.open_menu),  // Describes the menu button for accessibility.
                            tint = MaterialTheme.colorScheme.onSurfaceVariant  // The color of the icon, matching the theme.
                        )
                    }
                }
            }
        }
    )
}


@Preview()
@Composable
private fun PacePalToolbarPreview() {
    PacePalTheme {
        PacePalToolbar(
            modifier = Modifier.fillMaxWidth(),
            showBackButton = false,
            title = "PacePal",
            menuItems = listOf(
                DropdownMenuItem(
                    icon = AnalyticsIcon,
                    title = "Analytics"
                )
            ),
            onMenuItemClick = {},
            onBackClick = {},
            startContent = {
                Icon(
                    imageVector = LogoIcon,
                    contentDescription = null,
                    tint = PacePalBlue,
                    modifier = Modifier.size(35.dp)
                )
            })
    }

}