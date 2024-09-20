@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.nolawiworkineh.run.presentation.active_run

import android.Manifest
import android.content.Context
import android.os.Build
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nolawiworkineh.core.presentation.designsystem.PacePalTheme
import com.nolawiworkineh.core.presentation.designsystem.StartIcon
import com.nolawiworkineh.core.presentation.designsystem.StopIcon
import com.nolawiworkineh.core.presentation.designsystem.components.PacePalFloatingActionButton
import com.nolawiworkineh.core.presentation.designsystem.components.PacePalScaffold
import com.nolawiworkineh.core.presentation.designsystem.components.PacePalToolbar
import com.nolawiworkineh.run.presentation.R
import com.nolawiworkineh.run.presentation.active_run.components.RunDataCard
import com.nolawiworkineh.run.presentation.active_run.util.isLocationPermissionGranted
import com.nolawiworkineh.run.presentation.active_run.util.isNotificationPermissionGranted
import com.nolawiworkineh.run.presentation.active_run.util.shouldExplainLocationPermission
import com.nolawiworkineh.run.presentation.active_run.util.shouldExplainNotificationPermission
import org.koin.androidx.compose.koinViewModel

@Composable

fun ActiveRunScreenRoot(
    viewModel: ActiveRunViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    ActiveRunScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                ActiveRunAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }

    )
}

@Composable

private fun ActiveRunScreen(
    state: ActiveRunState,
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
                contentDescription = if(state.isTracking) {
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
            onAction = {}
        )
    }
}