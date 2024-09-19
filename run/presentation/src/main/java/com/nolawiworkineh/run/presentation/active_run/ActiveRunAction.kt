package com.nolawiworkineh.run.presentation.active_run

interface ActiveRunAction {
    // **OnToggleRunClick**: This action is triggered when the user clicks the toggle button to start or pause the run.
    data object OnToggleRunClick : ActiveRunAction

    // **OnFinishRunClick**: This action is triggered when the user finishes the run.
    data object OnFinishRunClick : ActiveRunAction

    // **OnResumeRunClick**: This action is triggered when the user resumes a paused run.
    data object OnResumeRunClick : ActiveRunAction

    // **OnBackClick**: This action is triggered when the user clicks the back button to exit the active run screen.
    data object OnBackClick : ActiveRunAction
}
