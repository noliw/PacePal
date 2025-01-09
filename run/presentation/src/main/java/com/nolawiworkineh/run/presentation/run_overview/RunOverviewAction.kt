package com.nolawiworkineh.run.presentation.run_overview

import com.nolawiworkineh.run.presentation.run_overview.model.RunUi

// all possible user actions on this screen
sealed interface RunOverviewAction {
    // **OnStartRunClick**: Represents the user clicking the "Start Run" button to begin tracking a run.
    data object OnStartRunClick : RunOverviewAction

    // **OnLogoutClick**: Represents the user clicking the "Log Out" button to log out of the app.
    data object OnLogoutClick : RunOverviewAction

    // **OnAnalyticsClick**: Represents the user clicking the "Analytics" button to view their run analytics.
    data object OnAnalyticsClick : RunOverviewAction

    data class DeleteRun(val runUi: RunUi): RunOverviewAction
}

