@file:OptIn(ExperimentalMaterial3Api::class)

package com.nolawiworkineh.run.presentation.run_overview

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nolawiworkineh.core.presentation.designsystem.PacePalTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun RunOverviewRoot(
    // **ViewModel Injection**: Retrieves the RunOverviewViewModel using Koin dependency injection.
    viewModel: RunOverviewViewModel = koinViewModel(),
) {
    // **RunOverviewScreen**: The main composable function for the screen, passing user actions to the ViewModel.
    RunOverviewScreen(
        onAction = viewModel::onAction  // The ViewModel will handle any user actions.
    )
}

@Composable
private fun RunOverviewScreen(
    // **onAction Function**: This takes in a function to handle user actions.
    onAction: (RunOverviewAction) -> Unit
) {
    // Currently, the screen is empty but we’ll build on this later.
}

@Preview
@Composable
private fun RunOverviewScreenPreview() {
    // **PacePalTheme**: Applies the app’s theme for styling.
    PacePalTheme {
        // **Preview Screen**: This allows us to preview the screen as we build it.
        RunOverviewScreen(
            onAction = {}  // No action is passed in the preview; it's for display purposes only.
        )
    }
}
