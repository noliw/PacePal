package com.nolawiworkineh.core.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun PacePalDialog(
    title: String,  // **Title**: The dialog's title (e.g., "Confirmation").
    onDismiss: () -> Unit,  // **onDismiss**: Function triggered when the user dismisses the dialog.
    description: String,  // **Description**: The dialog's main message to the user.
    primaryButton: @Composable () -> Unit,  //  Main button action (e.g., "Confirm").
    modifier: Modifier = Modifier, //Allows modification of the dialog's UI (e.g., padding, size).
    secondaryButton: @Composable () -> Unit = {} //Optional secondary button action (e.g., "Cancel").
) {
    // **Dialog Component**: Shows a system dialog.
    Dialog(onDismissRequest = onDismiss) {
        // **Column Layout**: Vertically stacks the dialog content.
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(15.dp))  // **Rounded Corners**: Gives the dialog rounded edges.
                .background(MaterialTheme.colorScheme.surface)  // **Background**: Uses the app's surface color.
                .padding(15.dp),  // **Padding**: Adds space around the dialog's content.
            verticalArrangement = Arrangement.spacedBy(12.dp),  // **Vertical Spacing**: Adds space between dialog elements.
            horizontalAlignment = Alignment.CenterHorizontally  // **Horizontal Alignment**: Centers dialog content.
        ) {
            // **Title Text**: Displays the dialog's title.
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,  // **Font Weight**: Sets the title to a semi-bold font.
                textAlign = TextAlign.Center,  // **Text Alignment**: Centers the title text.
                color = MaterialTheme.colorScheme.onSurface  // **Text Color**: Uses the appropriate surface text color.
            )

            // **Description Text**: Displays the dialog's description.
            Text(
                text = description,
                fontSize = 12.sp,  // **Font Size**: Smaller text size for the description.
                textAlign = TextAlign.Center,  // **Text Alignment**: Centers the description text.
                color = MaterialTheme.colorScheme.onSurfaceVariant  // **Text Color**: Uses the variant color for less emphasis.
            )

            // **Action Buttons Row**: Horizontally aligns the action buttons (primary and secondary).
            Row(
                modifier = Modifier
                    .fillMaxWidth(),  // **Fill Width**: Buttons take up the full width of the dialog.
                horizontalArrangement = Arrangement.spacedBy(16.dp),  // **Spacing Between Buttons**: Adds space between the buttons.
                verticalAlignment = Alignment.CenterVertically  // **Center Alignment**: Vertically centers the buttons.
            ) {
                secondaryButton()  // **Secondary Button**: Optional button, typically "Cancel".
                primaryButton()  // **Primary Button**: The main action button, typically "Confirm".
            }
        }
    }
}
