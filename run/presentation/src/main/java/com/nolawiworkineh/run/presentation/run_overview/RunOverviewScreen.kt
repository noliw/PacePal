@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.nolawiworkineh.run.presentation.run_overview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nolawiworkineh.core.presentation.designsystem.AnalyticsIcon
import com.nolawiworkineh.core.presentation.designsystem.LogoIcon
import com.nolawiworkineh.core.presentation.designsystem.LogoutIcon
import com.nolawiworkineh.core.presentation.designsystem.PacePalTheme
import com.nolawiworkineh.core.presentation.designsystem.RunIcon
import com.nolawiworkineh.core.presentation.designsystem.components.PacePalFloatingActionButton
import com.nolawiworkineh.core.presentation.designsystem.components.PacePalScaffold
import com.nolawiworkineh.core.presentation.designsystem.components.PacePalToolbar
import com.nolawiworkineh.core.presentation.designsystem.components.util.DropdownMenuItem
import com.nolawiworkineh.run.presentation.R
import com.nolawiworkineh.run.presentation.run_overview.components.RunListItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun RunOverviewRoot(
    // **ViewModel Injection**: Retrieves the RunOverviewViewModel using Koin dependency injection.
    viewModel: RunOverviewViewModel = koinViewModel(),
    onStartRunClick: () -> Unit
) {
    // **RunOverviewScreen**: The main composable function for the screen, passing user actions to the ViewModel.
    RunOverviewScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                RunOverviewAction.OnStartRunClick -> onStartRunClick()
                else -> Unit
            }
            // The ViewModel will handle any user actions.
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun RunOverviewScreen(
    // **onAction Function**: This function handles actions triggered by user interactions (like clicking a button).
    onAction: (RunOverviewAction) -> Unit,
    state: RunOverviewState,
) {
    // **rememberTopAppBarState**: Creates a state object to store and control the scroll behavior of the toolbar.
    val topAppBarState = rememberTopAppBarState()

    // **scrollBehavior**: Defines how the toolbar behaves when scrolling, such as hiding or remaining visible.
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState  // Passes the state that controls the scroll behavior.
    )

    // **PacePalScaffold**: The main layout structure of the screen, which handles the toolbar, FAB, and content.
    PacePalScaffold(
        topAppBar = {
            // **PacePalToolbar**: The custom toolbar for the app, showing the title, menu, and optional start content (logo).
            PacePalToolbar(
                showBackButton = false,  // Disables the back button since this is the main screen.
                title = stringResource(id = R.string.pacepal),  // Sets the title of the toolbar to "PacePal".
                scrollBehavior = scrollBehavior,  // Assigns the scroll behavior to the toolbar.

                // **menuItems**: Defines the items in the toolbar’s dropdown menu, like Analytics and Logout.
                menuItems = listOf(
                    DropdownMenuItem(
                        icon = AnalyticsIcon,  // Icon for the analytics menu item.
                        title = stringResource(id = R.string.analytics)  // Title for the analytics menu item.
                    ),
                    DropdownMenuItem(
                        icon = LogoutIcon,  // Icon for the logout menu item.
                        title = stringResource(id = R.string.logout)  // Title for the logout menu item.
                    ),
                ),

                // **onMenuItemClick**: Defines the actions when menu items are clicked.
                onMenuItemClick = { index ->
                    when (index) {
                        0 -> onAction(RunOverviewAction.OnAnalyticsClick)  // Handles Analytics click.
                        1 -> onAction(RunOverviewAction.OnLogoutClick)  // Handles Logout click.
                    }
                },

                // **startContent**: Displays the app’s logo at the start of the toolbar.
                startContent = {
                    Icon(
                        imageVector = LogoIcon,  // The logo icon of the app.
                        contentDescription = null,  // No need for a description here, as it’s just a visual element.
                        tint = MaterialTheme.colorScheme.primary,  // Sets the color of the logo to match the theme's primary color.
                        modifier = Modifier.size(30.dp)  // Adjusts the size of the logo.
                    )
                }
            )
        },

        // **floatingActionButton**: The main action button for starting a new run.
        floatingActionButton = {
            PacePalFloatingActionButton(
                icon = RunIcon,  // The icon for the floating action button (Run).
                onClick = {
                    onAction(RunOverviewAction.OnStartRunClick)  // Handles the start run action when the FAB is clicked.
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .padding(horizontal = 16.dp),
            contentPadding = padding,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = state.runs,
                key = { it.id }
            ) {
                RunListItem(
                    runUi = it,
                    onDeleteClick = {
                        onAction(RunOverviewAction.DeleteRun(it))
                    },
                    modifier = Modifier
                        .animateItemPlacement()
                )
            }
        }
    }
}


@Preview
@Composable
private fun RunOverviewScreenPreview() {
    // **PacePalTheme**: Applies the app’s theme for styling.
    PacePalTheme {
        // **Preview Screen**: This allows us to preview the screen as we build it.
        RunOverviewScreen(
            state = RunOverviewState(),
            onAction = {}  // No action is passed in the preview; it's for display purposes only.
        )
    }
}
