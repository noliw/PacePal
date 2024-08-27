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

// **PacePalTextField Function**: Creates a custom text field with additional features like icons and error handling.
@Composable
fun PacePalTextField(
    // **State**: Represents the current state of the text field, including the input text.
    state: TextFieldState,
    // **Start Icon**: Optional icon to display at the beginning of the text field.
    startIcon: ImageVector?,
    // **End Icon**: Optional icon to display at the end of the text field.
    endIcon: ImageVector?,
    // **Hint**: Placeholder text that appears when the text field is empty.
    hint: String,
    // **Title**: Optional title displayed above the text field.
    title: String?,
    // **Modifier**: Allows customization of the text field’s layout and appearance.
    modifier: Modifier = Modifier,
    // **Error**: Optional error message displayed when the input is invalid.
    error: String? = null,
    // **Keyboard Type**: Specifies the type of keyboard to use (e.g., text, number, email).
    keyboardType: KeyboardType = KeyboardType.Text,
// **Additional Info**: Optional extra information displayed next to the title.
    additionalInfo: String? = null
) {
    // **Focus State**: Tracks whether the text field is currently focused.
    var isFocused by remember {
        mutableStateOf(false)
    }

    // **Column**: Arranges the title, text field, and error/additional info vertically.
    Column(
        modifier = modifier
    ) {
        // **Row**: Arranges the title and error/additional info horizontally.
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // **Title**: Displays the title if provided.
            if(title != null) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            // **Error Message**: Displays the error message if provided.
            if(error != null) {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
                // **Additional Info**: Displays additional info if no error is present.
            } else if(additionalInfo != null) {
                Text(
                    text = additionalInfo,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp
                )
            }
        }
        // **Spacer**: Adds vertical spacing between the title row and the text field.
        Spacer(modifier = Modifier.height(4.dp))

        // **BasicTextField2**: Custom implementation of the text field.
        BasicTextField2(
            state = state,
            textStyle = LocalTextStyle.current.copy(
                color = MaterialTheme.colorScheme.onBackground
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            lineLimits = TextFieldLineLimits.SingleLine,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(
                    if (isFocused) {
                        MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.05f
                        )
                    } else {
                        MaterialTheme.colorScheme.surface
                    }
                )
                .border(
                    width = 1.dp,
                    color = if (isFocused) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        Color.Transparent
                    },
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            decorator = { innerBox ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // **Start Icon**: Displays the start icon if provided.
                    if(startIcon != null) {
                        Icon(
                            imageVector = startIcon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    // **Hint and Input**: Displays the hint when the text field is empty and unfocused.
                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        if(state.text.isEmpty() && !isFocused) {
                            Text(
                                text = hint,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                    alpha = 0.4f
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        innerBox()
                    }
                    // **End Icon**: Displays the end icon if provided.
                    if(endIcon != null) {
                        Spacer(modifier = Modifier.width(16.dp))
                        Icon(
                            imageVector = endIcon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
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