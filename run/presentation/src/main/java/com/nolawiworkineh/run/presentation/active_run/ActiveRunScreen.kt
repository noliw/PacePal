@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.nolawiworkineh.run.presentation.active_run

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nolawiworkineh.core.presentation.designsystem.PacePalTheme
import com.nolawiworkineh.core.presentation.designsystem.StartIcon
import com.nolawiworkineh.core.presentation.designsystem.StopIcon
import com.nolawiworkineh.core.presentation.designsystem.components.PacePalActionButton
import com.nolawiworkineh.core.presentation.designsystem.components.PacePalDialog
import com.nolawiworkineh.core.presentation.designsystem.components.PacePalFloatingActionButton
import com.nolawiworkineh.core.presentation.designsystem.components.PacePalOutlinedActionButton
import com.nolawiworkineh.core.presentation.designsystem.components.PacePalScaffold
import com.nolawiworkineh.core.presentation.designsystem.components.PacePalToolbar
import com.nolawiworkineh.presentation.ui.ObserveAsEvents
import com.nolawiworkineh.run.presentation.R
import com.nolawiworkineh.run.presentation.active_run.components.RunDataCard
import com.nolawiworkineh.run.presentation.active_run.maps.TrackerMap
import com.nolawiworkineh.run.presentation.active_run.service.ActiveRunService
import com.nolawiworkineh.run.presentation.active_run.util.isLocationPermissionGranted
import com.nolawiworkineh.run.presentation.active_run.util.isNotificationPermissionGranted
import com.nolawiworkineh.run.presentation.active_run.util.shouldExplainLocationPermission
import com.nolawiworkineh.run.presentation.active_run.util.shouldExplainNotificationPermission
import org.koin.androidx.compose.koinViewModel
import java.io.ByteArrayOutputStream

@Composable

fun ActiveRunScreenRoot(
    onFinish: () -> Unit,
    viewModel: ActiveRunViewModel = koinViewModel(),
    onServiceToggle: (isServiceRunning: Boolean) -> Unit,
    onBackClick: () -> Unit
) {
    // Get the current context (required for displaying Toast messages)
    val context = LocalContext.current

    // Observe events emitted by the ViewModel
    ObserveAsEvents(flow = viewModel.events) { event ->
        when (event) {
            // If an error event is emitted, show a Toast with the error message
            is ActiveRunEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.asString(context), // Converts the error to a user-readable string
                    Toast.LENGTH_LONG
                ).show()
            }

            // If a run-saving event is emitted, trigger the onFinish callback
            ActiveRunEvent.RunSaved -> onFinish()
        }
    }

    // Render the ActiveRunScreen composable
    ActiveRunScreen(
        state = viewModel.state, // Pass the state from the ViewModel to the UI
        onServiceToggle = onServiceToggle, // Pass the service toggle callback to the UI
        onAction = { action -> // Handle actions from the UI
            when (action) {
                // Handle the back button click
                is ActiveRunAction.OnBackClick -> {
                    if (!viewModel.state.hasStartedRunning) { // Allow back navigation only if the run hasn't started
                        onBackClick()
                    }
                }
                else -> Unit // No other actions are handled here
            }
            // Send the action to the ViewModel for further processing
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable

private fun ActiveRunScreen(
    state: ActiveRunState,
    onServiceToggle: (isServiceRunning: Boolean)-> Unit,
    onAction: (ActiveRunAction) -> Unit
) {
    // **Get the current context**: The context represents the current state of the application or activity.
    val context = LocalContext.current

// **Permission Launcher**: This is used to request multiple permissions at once and handle the response.
    val permissionLauncher = rememberLauncherForActivityResult(
        // **Request Multiple Permissions Contract**: Specifies that we want to request multiple permissions in one go.
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perms -> // **Callback for the result**: This code runs after the user responds to the permission request.

        // **Check if coarse location permission was granted**: `true` if granted, `false` if not.
        val hasCourseLocationPermission = perms[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        // **Check if fine location permission was granted**: `true` if granted, `false` if not.
        val hasFineLocationPermission = perms[Manifest.permission.ACCESS_FINE_LOCATION] == true

        // **Check notification permission**: Android 13 and above requires this permission.
        // For versions below 33, we don't need to ask, so it's always `true`.
        val isNotificationPermissionGranted = if (Build.VERSION.SDK_INT >= 33) {
            perms[Manifest.permission.POST_NOTIFICATIONS] == true
        } else true

        // **Convert context to ComponentActivity**: We need the activity to check if we should show a rationale.
        val activity = context as ComponentActivity

        // **Check if we need to show a rationale for location permission**:
        // If the user denied the permission before, Android may suggest explaining why we need it.
        val showLocationRationale = activity.shouldExplainLocationPermission()

        // **Check if we need to show a rationale for notification permission**:
        // Similar to location, this explains why notifications are important if the user previously denied them.
        val showNotificationRationale = activity.shouldExplainNotificationPermission()

        // **Submit the result of the location permission request** to the view model.
        onAction(
            ActiveRunAction.SubmitLocationPermissionInfo(
                // **acceptedLocationPermission**: `true` if both coarse and fine location permissions were granted.
                acceptedLocationPermission = hasCourseLocationPermission && hasFineLocationPermission,

                // **showLocationRationale**: Indicates if we should show an explanation for location permission.
                showLocationRationale = showLocationRationale
            )
        )

        // **Submit the result of the notification permission request** to the view model.
        onAction(
            ActiveRunAction.SubmitNotificationPermissionInfo(
                // **acceptedNotificationPermission**: `true` if the notification permission was granted (or not needed).
                acceptedNotificationPermission = isNotificationPermissionGranted,

                // **showNotificationPermissionRationale**: Indicates if we should show an explanation for notification permission.
                showNotificationPermissionRationale = showNotificationRationale
            )
        )
    }

    // **LaunchedEffect**: Runs the code inside this block when the screen is first composed or when the key changes.
    // Here, it's set to always run when the screen is opened (since `key1 = true`).
    LaunchedEffect(key1 = true) {

        // **Convert Context to Activity**: The `context` is cast to `ComponentActivity` to access permission-related methods
        // needed for checking permission status and rationale explanations.
        val activity = context as ComponentActivity

        // **showLocationRationale**: Determines if the app should show a rationale (explanation) for why location permissions are needed.
        val showLocationRationale = activity.shouldExplainLocationPermission()

        // **showNotificationRationale**: Determines if the app should show a rationale for notification permissions.
        val showNotificationRationale = activity.shouldExplainNotificationPermission()

        // **Submit location permission info**: Send the location permission status and whether we need to show a rationale.
        onAction(
            ActiveRunAction.SubmitLocationPermissionInfo(
                acceptedLocationPermission = context.isLocationPermissionGranted(), // Checks if location permissions are granted.
                showLocationRationale = showLocationRationale // Whether we should show an explanation to the user.
            )
        )

        // **Submit notification permission info**: Send the notification permission status and whether we need to show a rationale.
        onAction(
            ActiveRunAction.SubmitNotificationPermissionInfo(
                acceptedNotificationPermission = context.isNotificationPermissionGranted(), // Checks if notification permissions are granted.
                showNotificationPermissionRationale = showNotificationRationale // Whether we should show an explanation to the user.
            )
        )

        // **Re-request permissions**: If there is no rationale for either location or notification permissions, request them again.
        if(!showLocationRationale && !showNotificationRationale) {
            // **Request Permissions**: This will trigger the permission launcher to ask the user for the necessary permissions.
            permissionLauncher.requestPacePalPermissions(context)
        }
    }

    LaunchedEffect(key1 = state.isRunFinished) {
        if(state.isRunFinished) {
            onServiceToggle(false)
        }
    }

    LaunchedEffect(key1 = state.isTracking) {
        if(context.isLocationPermissionGranted() && state.isTracking && !ActiveRunService.isServiceActive) {
            onServiceToggle(true)
        }
    }

    PacePalScaffold(
        withGradient = false,
        topAppBar = {
            PacePalToolbar(
                showBackButton = true,
                title = stringResource(id = R.string.active_run),
                onBackClick = {
                    onAction(ActiveRunAction.OnBackClick)
                },
            )
        },
        floatingActionButton = {
            PacePalFloatingActionButton(
                icon = if (state.isTracking) StopIcon else StartIcon,
                onClick = {
                    onAction(ActiveRunAction.OnToggleRunClick)
                },
                iconSize = 20.dp,
                contentDescription = if (state.isTracking) {
                    stringResource(id = R.string.pause_run)
                } else {
                    stringResource(id = R.string.start_run)
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            TrackerMap(
                isRunFinished = state.isRunFinished,
                currentLocation = state.currentLocation,
                locations = state.runData.locations,
                onSnapshot = {bmp ->
                    val stream = ByteArrayOutputStream()
                    stream.use {
                        bmp.compress(
                            Bitmap.CompressFormat.JPEG,
                            80,
                            it
                        )
                    }
                    onAction(ActiveRunAction.OnRunProcessed(stream.toByteArray()))},
                modifier = Modifier
                    .fillMaxSize()
            )
            RunDataCard(
                elapsedTime = state.elapsedTime,
                runData = state.runData,
                modifier = Modifier
                    .padding(16.dp)
                    .padding(padding)
                    .fillMaxWidth()
            )
        }
    }
    if (!state.isTracking && state.hasStartedRunning) {
        PacePalDialog(
            title = stringResource(id = R.string.running_is_paused),
            onDismiss = {
                onAction(ActiveRunAction.OnResumeRunClick)
            },
            description = stringResource(id = R.string.resume_or_finish_run),
            primaryButton = {
                PacePalActionButton(
                    text = stringResource(id = R.string.resume),
                    isLoading = false,
                    onClick = {
                        onAction(ActiveRunAction.OnResumeRunClick)
                    },
                    modifier = Modifier.weight(1f)
                )
            },
            secondaryButton = {
                PacePalOutlinedActionButton(
                    text = stringResource(id = R.string.finish),
                    isLoading = state.isSavingRun,
                    onClick = {
                        onAction(ActiveRunAction.OnFinishRunClick)
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        )
    }

    // Check if we need to show a permission rationale dialog for location or notification permissions.
    if (state.showLocationRationale || state.showNotificationRationale) {

        // Display a custom dialog to explain why these permissions are necessary.
        PacePalDialog(
            // Set the title of the dialog to "Permission Required".
            title = stringResource(id = R.string.permission_required),

            // Disable normal dismissing for the dialog, as dismissing permission-related dialogs isn't allowed.
            onDismiss = { /* Disable Dismissal: Normal dismissing not allowed for permissions */ },

            // Set the description of the dialog based on which rationale(s) need to be shown.
            description = when {
                // If both location and notification permissions need a rationale, show a combined message.
                state.showLocationRationale && state.showNotificationRationale -> {
                    stringResource(id = R.string.location_notification_rationale)
                }
                // If only location permission needs a rationale, show the location-specific message.
                state.showLocationRationale -> {
                    stringResource(id = R.string.location_rationale)
                }
                // Otherwise, show the notification-specific message.
                else -> {
                    stringResource(id = R.string.notification_rationale)
                }
            },

            // Define the primary button that the user clicks to proceed.
            primaryButton = {
                // Use an outlined action button to give the user a clear "Okay" option to move forward.
                PacePalOutlinedActionButton(
                    // The button label is set to "Okay".
                    text = stringResource(id = R.string.okay),

                    // The button isn't in a loading state, so set isLoading to false.
                    isLoading = false,

                    // When the button is clicked:
                    onClick = {
                        // Dismiss the rationale dialog by dispatching the DismissRationaleDialog action.
                        onAction(ActiveRunAction.DismissRationaleDialog)

                        // Re-initiate the permission request flow by launching the permission request function.
                        permissionLauncher.requestPacePalPermissions(context)
                    }
                )
            }
        )
    }

}

// Extension function for ActivityResultLauncher to handle multiple permissions for PacePal.
private fun ActivityResultLauncher<Array<String>>.requestPacePalPermissions(
    context: Context
) {
    // **isLocationPermissionGranted**: Check if location permissions (either fine or coarse) are already granted.
    val isLocationPermissionGranted = context.isLocationPermissionGranted()
    // **isNotificationPermissionGranted**: Check if notification permission is granted. For Android 13+ only.
    val isNotificationPermissionGranted = context.isNotificationPermissionGranted()
    // **locationPermissions**: Array containing the two location permissions that the app needs to request.
    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION, // **COARSE LOCATION**: Less accurate location data.
        Manifest.permission.ACCESS_FINE_LOCATION    // **FINE LOCATION**: More accurate GPS data.
    )
    // **notificationPermission**: Only request notification permissions on Android 13+ (API level 33 or higher).
    val notificationPermission = if (Build.VERSION.SDK_INT >= 33) {
        arrayOf(Manifest.permission.POST_NOTIFICATIONS) // Request notification permissions for Android 13+.
    } else arrayOf() // Empty array if the Android version is lower than 33 (no need for this permission).
    // **when block**: Decide which permissions to request based on the current permission status.
    when {
        // **Case 1**: Neither location nor notification permissions are granted. Request both.
        !isLocationPermissionGranted && !isNotificationPermissionGranted -> {
            launch(locationPermissions + notificationPermission) // Launch permission dialog for both location and notifications.
        }
        // **Case 2**: Only location permissions are not granted. Request just the location permissions.
        !isLocationPermissionGranted -> launch(locationPermissions) // Launch permission dialog for location permissions only.
        // **Case 3**: Only notification permission is not granted. Request just the notification permission.
        !isNotificationPermissionGranted -> launch(notificationPermission) // Launch permission dialog for notification permissions only.
    }
}


@Preview
@Composable
private fun ActiveRunScreenPreview() {
    PacePalTheme {
        ActiveRunScreen(
            state = ActiveRunState(),
            onServiceToggle = {},
            onAction = {}
        )
    }
}