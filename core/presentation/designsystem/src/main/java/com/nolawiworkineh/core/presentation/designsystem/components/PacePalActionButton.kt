package com.nolawiworkineh.core.presentation.designsystem.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nolawiworkineh.core.presentation.designsystem.PacePalBlack
import com.nolawiworkineh.core.presentation.designsystem.PacePalBlue30
import com.nolawiworkineh.core.presentation.designsystem.PacePalGray

// **PacePalActionButton Composable Function**: A customizable filled action button with a loading indicator.
@Composable
fun PacePalActionButton(
    text: String, // **Button Text**: The text label displayed on the button.
    isLoading: Boolean, // **Loading State**: Indicates if the button is in a loading state.
    modifier: Modifier = Modifier, // **Modifier**: Allows customization of the button's appearance and behavior.
    enabled: Boolean = true, // **Enabled State**: Determines if the button is clickable.
    onClick: () -> Unit // **Click Listener**: The action to perform when the button is clicked.
) {
    // **Button Composable**: Creates a filled button with customizable colors, shape, and content.
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = PacePalBlue30, // The background color of the button.
            contentColor = MaterialTheme.colorScheme.onPrimary, // The text color of the button.
            disabledContainerColor = PacePalGray, // The background color when the button is disabled.
            disabledContentColor = PacePalBlack // The text color when the button is disabled.
        ),
        shape = RoundedCornerShape(100f), // **Button Shape**: The button's corners are rounded with a large radius.
        modifier = modifier
            .height(IntrinsicSize.Min) // **Height Adjustment**: Ensures the button's height is dynamically adjusted.
    ) {
        // **Box Composable**: Contains the button's content, including the loading indicator and text.
        Box(
            modifier = Modifier
                .fillMaxWidth() // **Full-Width**: Ensures the button takes up the full width of its parent.
                .padding(vertical = 8.dp), // **Vertical Padding**: Adds padding to the top and bottom of the button content.
            contentAlignment = Alignment.Center // **Center Alignment**: Centers the content within the button.
        ) {
            // **Loading Indicator**: A circular progress indicator that appears when the button is loading.
            CircularProgressIndicator(
                modifier = Modifier
                    .size(15.dp) // **Size**: Sets the size of the loading indicator.
                    .alpha(if (isLoading) 1f else 0f), // **Visibility**: Shows or hides the indicator based on the loading state.
                strokeWidth = 1.5.dp, // **Stroke Width**: Sets the thickness of the loading indicator.
                color = MaterialTheme.colorScheme.onPrimary // **Indicator Color**: Matches the button's text color.
            )
            // **Button Text**: The text label of the button, hidden when the loading indicator is visible.
            Text(
                text = text,
                modifier = Modifier
                    .alpha(if(isLoading) 0f else 1f), // **Visibility**: Hides the text when the loading indicator is visible.
                fontWeight = FontWeight.Medium, // **Font Weight**: Sets the thickness of the text.
                color = MaterialTheme.colorScheme.onBackground // **Text Color**: The color of the text.
            )
        }
    }
}

// **PacePalOutlinedActionButton Composable Function**: A customizable outlined action button with a loading indicator.
@Composable
fun PacePalOutlinedActionButton(
    text: String, // **Button Text**: The text label displayed on the button.
    isLoading: Boolean, // **Loading State**: Indicates if the button is in a loading state.
    modifier: Modifier = Modifier, // **Modifier**: Allows customization of the button's appearance and behavior.
    enabled: Boolean = true, // **Enabled State**: Determines if the button is clickable.
    onClick: () -> Unit // **Click Listener**: The action to perform when the button is clicked.
) {
    // **OutlinedButton Composable**: Creates an outlined button with customizable border, colors, shape, and content.
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground // **Text Color**: The text color of the button.
        ),
        border = BorderStroke(
            width = 0.5.dp, // **Border Width**: Sets the thickness of the button's border.
            color = MaterialTheme.colorScheme.onBackground // **Border Color**: Matches the text color.
        ),
        shape = RoundedCornerShape(100f), // **Button Shape**: The button's corners are rounded with a large radius.
        modifier = modifier
            .height(IntrinsicSize.Min) // **Height Adjustment**: Ensures the button's height is dynamically adjusted.
    ) {
        // **Box Composable**: Contains the button's content, including the loading indicator and text.
        Box(
            modifier = Modifier
                .fillMaxWidth() // **Full-Width**: Ensures the button takes up the full width of its parent.
                .padding(vertical = 8.dp), // **Vertical Padding**: Adds padding to the top and bottom of the button content.
            contentAlignment = Alignment.Center // **Center Alignment**: Centers the content within the button.
        ) {
            // **Loading Indicator**: A circular progress indicator that appears when the button is loading.
            CircularProgressIndicator(
                modifier = Modifier
                    .size(15.dp) // **Size**: Sets the size of the loading indicator.
                    .alpha(if (isLoading) 1f else 0f), // **Visibility**: Shows or hides the indicator based on the loading state.
                strokeWidth = 1.5.dp, // **Stroke Width**: Sets the thickness of the loading indicator.
                color = MaterialTheme.colorScheme.onBackground // **Indicator Color**: Matches the button's text color.
            )
            // **Button Text**: The text label of the button, hidden when the loading indicator is visible.
            Text(
                text = text,
                modifier = Modifier
                    .alpha(if(isLoading) 0f else 1f), // **Visibility**: Hides the text when the loading indicator is visible.
                fontWeight = FontWeight.Medium // **Font Weight**: Sets the thickness of the text.
            )
        }
    }
}
