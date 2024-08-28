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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.TextFieldLineLimits
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nolawiworkineh.core.presentation.designsystem.CheckIcon
import com.nolawiworkineh.core.presentation.designsystem.EmailIcon
import com.nolawiworkineh.core.presentation.designsystem.PacePalTheme
import com.nolawiworkineh.core.presentation.designsystem.VerticalSpacer

// **PacePalTextField Function**: Creates a custom text field with additional features like icons and error handling.
@Composable
// **PacePalTextField Function**: A custom text field composable for the Pacepal app.
fun PacePalTextField(
    state: TextFieldState, // **State**: Holds the current text and other properties of the text field.
    startIcon: ImageVector?, // **Start Icon**: An optional icon displayed at the beginning of the text field.
    endIcon: ImageVector?, // **End Icon**: An optional icon displayed at the end of the text field.
    hint: String, // **Hint**: A placeholder text shown when the text field is empty.
    title: String?, // **Title**: An optional label displayed above the text field.
    modifier: Modifier = Modifier, // **Modifier**: Allows for custom styling and layout modifications.
    error: String? = null, // **Error**: An optional error message displayed when the input is invalid.
    keyboardType: KeyboardType = KeyboardType.Text, // **Keyboard Type**: Specifies the type of keyboard to be shown.
    additionalInfo: String? = null // **Additional Info**: An optional informational message displayed above the text field.
) {
    var isFocused by remember {
        // **Focus State**: Tracks whether the text field is currently focused.
        mutableStateOf(false)
    }
    Column(
        // **Column Layout**: Arranges the text field and associated components vertically.
        modifier = modifier
    ) {
        Row(
            // **Row Layout**: Arranges the title and error/additional info horizontally.
            modifier = Modifier
                .fillMaxWidth(),
            // **Arrangement**: Distributes space between components.
            horizontalArrangement = Arrangement.SpaceBetween,
            // **Alignment**: Vertically centers the components in the row.
            verticalAlignment = Alignment.CenterVertically
        ) {
            // if the title is not null, display the title
            if(title != null) {
                Text(
                    // **Title Text**: Displays the label above the text field.
                    text = title,
                    // **Title Color**: Uses the appropriate color from the theme.
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            // if there is an error or additional info, display them
            if(error != null) {
                Text(
                    // **Error Text**: Displays the error message in a specific style.
                    text = error,
                    // **Error Color**: Uses the error color from the theme.
                    color = MaterialTheme.colorScheme.error,
                    // **Error Font Size**: Sets the font size for the error text.
                    fontSize = 12.sp
                )
            } else if(additionalInfo != null) {
                Text(
                    // **Additional Info Text**: Displays additional information.
                    text = additionalInfo,
                    // **Info Color**: Uses a muted color from the theme.
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    // **Info Font Size**: Sets the font size for the additional information.
                    fontSize = 12.sp
                )
            }
        }
        VerticalSpacer(height = 4) // **Spacer**: Adds vertical space between components.
        BasicTextField2(
            // **Text Field State**: Binds the state of the text field.
            // **State**: Holds the current text and other properties of the text field.
            state = state,
            textStyle = LocalTextStyle.current.copy(
                // **Text Style**: Applies the current theme's text style.
                color = MaterialTheme.colorScheme.onBackground
            ),
            // **Keyboard Options**: Configures the keyboard type (e.g., text, number).
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            // **Line Limit**: Restricts input to a single line.
            lineLimits = TextFieldLineLimits.SingleLine,
            // **Cursor Color**: Sets the color of the text cursor.
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp)) // **Shape**: Rounds the corners of the text field.
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
                    // **Border Color**: Configures the border color based on focus.
                    // if the text field is focused, use the primary color
                    color = if (isFocused) {
                        // **Focus Border Color**: Changes border color when focused.
                        MaterialTheme.colorScheme.primary
                    } else {
                        // **Default Border Color**: No visible border when not focused.
                        Color.Transparent
                    },
                    // **Border Shape**: Applies the rounded shape to the border.
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp) // **Padding**: Adds padding inside the text field.
                // onFocusChanged: Callback triggered when the focus state changes.
                .onFocusChanged {
                    // **Focus State**: Tracks whether the text field is currently focused.
                    // **Focus Change**: Updates the focus state when focus changes.
                    isFocused =
                        it.isFocused
                },
            // decorator: Decorates the inner content of the text field.
            // simply it changes the layout of the text field.
            decorator = { innerBox ->
                // **Decorator Row**: Layout for the icons and text inside the text field.
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    // **Alignment**: Centers the content vertically.
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // if there is a start icon, display it
                    if(startIcon != null) {
                        Icon(
                            // **Start Icon**: Displays the start icon if provided.
                            imageVector = startIcon,
                            // **Description**: Omits content description for this icon.
                            contentDescription = null,
                            // **Icon Tint**: Uses a theme color for the icon.
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        // **Spacer**: Adds horizontal space between the icon and text.
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    // this is the actual text field
                    Box(
                        // **Weight Modifier**: Allows the text area to fill the remaining space.
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        // if the text field is empty and not focused, display the hint
                        if(state.text.isEmpty() && !isFocused) {
                            Text(
                                //  Displays the hint when the text field is empty and unfocused.
                                text = hint,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                    // Makes the hint text partially transparent.
                                    alpha = 0.4f
                                ),
                                // **Hint Modifier**: Fills the width of the text area.
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        // **Inner Box**: Placeholder for the actual text input.
                        // this is where the user types
                        innerBox()
                    }
                    if(endIcon != null) {
                        // **Spacer**: Adds space between the text and the end icon.
                        Spacer(modifier = Modifier.width(16.dp))
                        Icon(
                            // **End Icon**: Displays the end icon if provided.
                            imageVector = endIcon,
                            // **Description**: Omits content description for this icon.
                            contentDescription = null,
                            // **Icon Tint**: Uses a theme color for the icon.
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            // **Icon Padding**: Adds padding around the end icon.
                            modifier = Modifier
                                .padding(end = 8.dp)
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
        PacePalTextField(
            state = rememberTextFieldState(),
            startIcon = EmailIcon,
            endIcon = CheckIcon,
            hint = "example@test.com",
            title = "Email",
            additionalInfo = "Must be a valid email",
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}