package com.nolawiworkineh.run.presentation.active_run

interface ActiveRunAction {
    data object OnToggleRunClick: ActiveRunAction
    data object OnFinishRunClick: ActiveRunAction
    data object OnResumeRunClick: ActiveRunAction
    data object OnBackClick: ActiveRunAction
}