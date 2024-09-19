package com.nolawiworkineh.run.presentation.active_run

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nolawiworkineh.core.presentation.designsystem.PacePalTheme
import org.koin.androidx.compose.koinViewModel

@Composable

fun ActiveRunScreenRoot(
    viewModel: ActiveRunViewModel = koinViewModel()
) {
    ActiveRunScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable

private fun ActiveRunScreen(
    state: ActiveRunState,
    onAction: (ActiveRunAction) -> Unit
) {

}

@Preview
@Composable
private fun ActiveRunScreenPreview() {
    PacePalTheme {
        ActiveRunScreen(
            state = ActiveRunState(),
            onAction = {}
        )
    }
}