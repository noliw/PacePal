@file:OptIn(ExperimentalFoundationApi::class)

package com.nolawiworkineh.core.presentation.designsystem.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text2.BasicSecureTextField
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.TextObfuscationMode
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nolawiworkineh.core.presentation.designsystem.EyeClosedIcon
import com.nolawiworkineh.core.presentation.designsystem.EyeOpenedIcon
import com.nolawiworkineh.core.presentation.designsystem.LockIcon
import com.nolawiworkineh.core.presentation.designsystem.PacePalTheme
import com.nolawiworkineh.core.presentation.designsystem.R

// **PacePalPasswordTextField Composable**: A custom text field specifically for password input in the Pacepal app.
@Composable
fun PacePalPasswordTextField(
    // **State**: Holds the current text and other properties of the password field.
    state: TextFieldState,
    // **Visibility State**: Determines whether the password is visible or hidden.
    isPasswordVisible: Boolean,
    // **Visibility Toggle Callback**: Function to toggle the password visibility.
    onTogglePasswordVisibility: () -> Unit,
    // **Hint**: Placeholder text shown when the text field is empty.
    hint: String,
    // **Title**: Optional label displayed above the text field.
    title: String?,
    // **Modifier**: Allows for custom styling and layout modifications.
    modifier: Modifier = Modifier,
) {
    // **Focus State**: Tracks whether the password field is currently focused.
    var isFocused by remember {
        mutableStateOf(false)
    }
    // **Column Layout**: Arranges the password field components vertically.
    Column(
        modifier = modifier
    ) {
        Row(
            // **Row Layout**: Arranges the title horizontally across the width.
            modifier = Modifier
                .fillMaxWidth(),
            // **Arrangement**: Distributes space between components.
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            // if the title is not null, display the title
            if (title != null) {
                Text(
                    // **Title Text**: Displays the label above the password field.
                    text = title,
                    // **Title Color**: Uses the appropriate color from the theme.
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        // **Spacer**: Adds vertical space between the title and the password field.
        Spacer(modifier = Modifier.height(4.dp))
        // basic secure text field is a text field that hides the text
        BasicSecureTextField(
            // **Password Field State**: Binds the state of the password field.
            state = state,
            // obfuscation mode means that the text is hidden
            // if the password is visible, the text is visible
            textObfuscationMode = if (isPasswordVisible) {
                // **Visibility Mode**: Shows the password text if visible.
                TextObfuscationMode.Visible
                // **Visibility Mode**: Hides the password text if not visible.
            } else TextObfuscationMode.Hidden,
            textStyle = LocalTextStyle.current.copy(
                // **Text Style**: Applies the current theme's text style.
                color = MaterialTheme.colorScheme.onBackground
            ),
            // **Keyboard Type**: Configures the keyboard for password input.
            keyboardType = KeyboardType.Password,
            // **Cursor Color**: Sets the color of the text cursor.
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
            // **Shape**: Rounds the corners of the password field.
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                // **Background Color**: Applies a background color based on focus.
                .background(
                    // if the text field is focused, use a slightly transparent color
                    if (isFocused) {
                        MaterialTheme.colorScheme.primary.copy(
                            // **Focus Background**: Changes background color when focused.
                            alpha = 0.05f
                        )
                        // otherwise, use the surface color
                    } else {
                        // **Default Background**: Uses the surface color when not focused.
                        MaterialTheme.colorScheme.surface
                    }
                )
                .border(
                    width = 1.dp, // **Border Width**: Sets the width of the border.
                    // if the text field is focused, use the primary color
                    color = if (isFocused) {
                        // **Focus Border Color**: Changes border color when focused.
                        MaterialTheme.colorScheme.primary
                        // otherwise, use the surface color
                    } else {
                        // **Default Border Color**: No visible border when not focused.
                        Color.Transparent
                    },
                    // **Border Shape**: Applies the rounded shape to the border.
                    shape = RoundedCornerShape(16.dp)
                )
                // **Padding**: Adds horizontal padding inside the password field.
                .padding(horizontal = 12.dp)
                // when the text field is focused, change the background color
                .onFocusChanged {
                    // **Focus Change**: Updates the focus state when focus changes.
                    isFocused = it.isFocused
                },
            decorator = { innerBox ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(), // **Decorator Row**: Layout for the icons and text inside the password field.
                    verticalAlignment = Alignment.CenterVertically // **Alignment**: Centers the content vertically.
                ) {
                    Icon(
                        imageVector = LockIcon, // **Lock Icon**: Displays a lock icon indicating the field is for passwords.
                        contentDescription = null, // **Description**: Omits content description for this icon.
                        tint = MaterialTheme.colorScheme.onSurfaceVariant // **Icon Tint**: Uses a theme color for the icon.
                    )
                    Spacer(modifier = Modifier.width(16.dp)) // **Spacer**: Adds horizontal space between the icon and text.
                    Box(
                        modifier = Modifier
                            .weight(1f) // **Weight Modifier**: Allows the text area to fill the remaining space.
                    ) {
                        if (state.text.isEmpty() && !isFocused) {
                            Text(
                                text = hint, // **Hint Text**: Displays the hint when the password field is empty and unfocused.
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                    alpha = 0.4f // **Hint Transparency**: Makes the hint text partially transparent.
                                ),
                                modifier = Modifier.fillMaxWidth() // **Hint Modifier**: Fills the width of the text area.
                            )
                        }
                        innerBox() // **Inner Box**: Placeholder for the actual password input.
                    }
                    IconButton(onClick = onTogglePasswordVisibility) {
                        Icon(
                            imageVector = if (!isPasswordVisible) {
                                EyeClosedIcon // **Closed Eye Icon**: Displays when the password is hidden.
                            } else EyeOpenedIcon, // **Opened Eye Icon**: Displays when the password is visible.
                            contentDescription = if(isPasswordVisible) {
                                stringResource(id = R.string.show_password) // **Content Description**: Describes the action for accessibility.
                            } else {
                                stringResource(id = R.string.hide_password) // **Content Description**: Describes the action for accessibility.
                            },
                            tint = MaterialTheme.colorScheme.onSurfaceVariant // **Icon Tint**: Uses a theme color for the icon.
                        )
                    }
                }
            }
        )
    }
}


@Preview
@Composable
private fun PacePalTextFieldPreview() {
    PacePalTheme {
        PacePalPasswordTextField(
            state = rememberTextFieldState(),
            hint = "password",
            title = "Password",
            modifier = Modifier
                .fillMaxWidth(),
            isPasswordVisible = false,
            onTogglePasswordVisibility = {}
        )
    }
}