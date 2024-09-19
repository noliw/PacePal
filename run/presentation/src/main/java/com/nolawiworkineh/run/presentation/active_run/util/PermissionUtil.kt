package com.nolawiworkineh.run.presentation.active_run.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat

// **Checks if we should show a rationale for the location permission.**
// This is useful when the user has previously denied the permission,
// and we want to explain why the app needs it before asking again.
fun ComponentActivity.shouldShowLocationPermissionRationale(): Boolean {
    // **`shouldShowRequestPermissionRationale`** is a system function that returns `true`
    // if the user previously denied the permission and didn't check "Don't ask again".
    // It helps us decide if we need to show a rationale.
    return shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
}

// **Checks if we should show a rationale for the notification permission.**
// This is for Android 13 (API 33) and above, where notification permission is required.
fun ComponentActivity.shouldShowNotificationPermissionRationale(): Boolean {
    // **Checks if the device is running Android 13 (API 33) or higher**.
    // If the device is running an older version, the permission is not required.
    return Build.VERSION.SDK_INT >= 33 &&
            // **`shouldShowRequestPermissionRationale`** is used to check if the notification permission
            // was denied previously. If it returns `true`, we can show a rationale to the user.
            shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
}

// **Private function to check if a specific permission has been granted.**
// This is a utility function used by other parts of the app to check for different permissions.
private fun Context.hasPermission(permission: String): Boolean {
    // **Uses `ContextCompat.checkSelfPermission()` to check if the permission has been granted.**
    // The function compares the result with `PackageManager.PERMISSION_GRANTED` to verify
    // if the permission was granted by the user.
    return ContextCompat.checkSelfPermission(
        // **`this` refers to the context in which the function is called.**
        this,
        // **`permission` is the specific permission we are checking (e.g., location, notifications).**
        permission
    ) == PackageManager.PERMISSION_GRANTED // **Returns `true` if the permission is granted.**
}

// **Checks if the app has fine location permission.**
// This is a specific use of the `hasPermission()` function for location tracking.
fun Context.hasLocationPermission(): Boolean {
    // **Calls the `hasPermission` function with the fine location permission string.**
    // It checks if the app has permission to access the user's precise location.
    return hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
}

// **Checks if the app has notification permission for devices running Android 13 or higher.**
// For devices running older Android versions, it returns `true` because notification permission
// is not required.
fun Context.hasNotificationPermission(): Boolean {
    // **Checks if the device is running Android 13 (API 33) or higher.**
    return if (Build.VERSION.SDK_INT >= 33) {
        // **If the device is running Android 13 or higher, call `hasPermission` to check
        // if the notification permission has been granted.**
        hasPermission(Manifest.permission.POST_NOTIFICATIONS)
    } else true // **For older devices (pre-Android 13), return `true` as permission is not required.**
}
