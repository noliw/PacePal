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

    // **SubmitLocationPermissionInfo**: Represents the result of the location permission request.
    // It tracks whether the user accepted or denied the permission and whether we need to show a rationale.
    data class SubmitLocationPermissionInfo(
        val acceptedLocationPermission: Boolean,  // **true** if the user granted location access, **false** if denied.
        val showLocationRationale: Boolean  // **true** if the app needs to show a rationale for the permission.
    ): ActiveRunAction

    // **SubmitNotificationPermissionInfo**: Represents the result of the notification permission request.
    // It tracks whether the user accepted or denied the permission and whether we need to show a rationale.
    data class SubmitNotificationPermissionInfo(
        val acceptedNotificationPermission: Boolean,  // **true** if the user granted notification access, **false** if denied.
        val showNotificationPermissionRationale: Boolean  // **true** if the app needs to show a rationale for notifications.
    ): ActiveRunAction

    // **DismissRationaleDialog**: Action triggered when the user dismisses a rationale dialog,
    // which explains why the app needs certain permissions.
    data object DismissRationaleDialog: ActiveRunAction

    class OnRunProcessed(val mapPictureBytes: ByteArray): ActiveRunAction
}
