package com.nolawiworkineh.run.presentation.run_overview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nolawiworkineh.core.presentation.designsystem.PacePalTheme
import org.koin.androidx.compose.koinViewModel

@Composable

fun RunOverview(
    viewModel: RunOverviewViewModel = koinViewModel(),
) {
    RunOverviewScreen(
        onAction = viewModel::onAction
    )
}

@Composable

private fun RunOverviewScreen(
    onAction: (RunOverviewAction) -> Unit
) {

}

@Preview
@Composable
private fun RunOverviewScreenPreview() {
    PacePalTheme {
        RunOverviewScreen(
            onAction = {}
        )
    }
}