package com.nolawiworkineh.presentation.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

//  A flexible interface to represent different types of text that can be displayed in the UI.
sealed interface UiText {

    // Represents a string that is generated at runtime.
    data class DynamicString(val value: String): UiText

    // Represents a string resource from the app's resources.
    class StringResource(
        @StringRes val id: Int,
        val args: Array<Any> = arrayOf()
    ): UiText

    // Converts the UiText to a plain string for use in Compose UI.
    @Composable
    fun asString(): String {
        return when(this) {
            // If the text is dynamic, return the value directly.
            is DynamicString -> value
            // If the text is a resource, retrieve it using stringResource.
            is StringResource -> stringResource(id = id, *args)
        }
    }

    // Converts the UiText to a plain string for use in traditional Android views.
    fun asString(context: Context): String {
        return when(this) {
            // If the text is dynamic, return the value directly.
            is DynamicString -> value
            // If the text is a resource, retrieve it using context.getString.
            is StringResource -> context.getString(id, *args)
        }
    }
}
