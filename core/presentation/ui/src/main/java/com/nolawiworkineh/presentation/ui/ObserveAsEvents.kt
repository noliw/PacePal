package com.nolawiworkineh.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

// Mark the function as composable,making it part of the Jetpack Compose UI system.
@Composable
// Declare a generic function `ObserveAsEvents` that can observe events of any type `T`.
fun <T> ObserveAsEvents(
    // Accept a `Flow` of type `T`, representing the stream of events to be observed.
    flow: Flow<T>,
    // Optional key to control when the `LaunchedEffect` should restart, defaulting to null.
    key1: Any? = null,
    // Another optional key for controlling the restart of `LaunchedEffect`, also defaulting to null.
    key2: Any? = null,
    // Define a lambda function `onEvent` that specifies what to do when a new event is observed.
    onEvent: (T) -> Unit
) {
    // Get the current `LifecycleOwner` from the composition's context.
    val lifecycleOwner = LocalLifecycleOwner.current

    // Start a `LaunchedEffect` that reacts to changes in `flow`, `lifecycle`, `key1`, and `key2`.
    LaunchedEffect(flow, lifecycleOwner.lifecycle, key1, key2) {
        // Ensure the `Flow` is only collected when the `LifecycleOwner` is at least in the `STARTED` state.
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            // Switch the coroutine context to the main thread immediately to perform UI-related actions.
            withContext(Dispatchers.Main.immediate) {
                // Collect values from the `Flow` and pass each emitted value to the `onEvent` lambda.
                flow.collect(onEvent)
            }
        }
    }
}
