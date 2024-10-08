@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")
@file:OptIn(ExperimentalFoundationApi::class)

package com.nolawiworkineh.auth.presentation.register

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nolawiworkineh.auth.domain.PasswordValidationState
import com.nolawiworkineh.auth.domain.UserDataValidator
import com.nolawiworkineh.auth.presentation.R
import com.nolawiworkineh.core.presentation.designsystem.CheckIcon
import com.nolawiworkineh.core.presentation.designsystem.CrossIcon
import com.nolawiworkineh.core.presentation.designsystem.EmailIcon
import com.nolawiworkineh.core.presentation.designsystem.PacePalBlue
import com.nolawiworkineh.core.presentation.designsystem.PacePalDarkRed
import com.nolawiworkineh.core.presentation.designsystem.PacePalTheme
import com.nolawiworkineh.core.presentation.designsystem.PacePalWhite
import com.nolawiworkineh.core.presentation.designsystem.Poppins
import com.nolawiworkineh.core.presentation.designsystem.VerticalSpacer
import com.nolawiworkineh.core.presentation.designsystem.components.GradientBackground
import com.nolawiworkineh.core.presentation.designsystem.components.PacePalActionButton
import com.nolawiworkineh.core.presentation.designsystem.components.PacePalPasswordTextField
import com.nolawiworkineh.core.presentation.designsystem.components.PacePalTextField
import com.nolawiworkineh.presentation.ui.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

// **RegisterScreenRoot Composable**: The root composable for the registration screen in the Pacepal app.
@Composable
fun RegisterScreenRoot(
    // **ViewModel Injection**: Injects the RegisterViewModel using Koin dependency injection.
    viewModel: RegisterViewModel = koinViewModel(),
    // **Sign-In Callback**: A lambda function that handles navigation to the sign-in screen.
    onSignInClick: () -> Unit,
    // **Success Callback**: A lambda function that handles actions after successful registration.
    onSuccessfulRegistration: () -> Unit
) {
    // **Current Context**: Retrieves the current context to display Toast messages or interact with the system.
    val context = LocalContext.current
    // **Keyboard Controller**: Manages the software keyboard, allowing the app to show or hide the keyboard.
    val keyboardController = LocalSoftwareKeyboardController.current
    // **Observing Events from ViewModel**: Listens to events emitted by the ViewModel and responds (like showing errors or success).
    ObserveAsEvents(flow = viewModel.events) { event ->
        when (event) {
            // **Error Event**: Handles errors like invalid email, server issues, etc.
            is RegisterEvent.Error -> {
                keyboardController?.hide()  // **Hide Keyboard**: Hides the keyboard when an error occurs.
                // **Show Error Message**: Displays the error as a Toast.
                Toast.makeText(context, event.error.asString(context), Toast.LENGTH_LONG).show()
            }
            // **Success Event**: Handles successful registration.
            RegisterEvent.RegistrationSuccess -> {
                keyboardController?.hide()  // **Hide Keyboard**: Hides the keyboard upon success.
                // **Show Success Message**: Displays a success message.
                Toast.makeText(context, R.string.registration_successfull, Toast.LENGTH_LONG).show()
                // **Trigger Success Callback**: Calls the provided function after successful registration (e.g., navigate to another screen).
                onSuccessfulRegistration()
            }
        }
    }

    // **Register Screen**: Displays the registration form and handles the UI interaction.
    RegisterScreen(
        // **State Propagation**: Passes the current state (email, password, validation, etc.) from the ViewModel.
        state = viewModel.state,

        // **Action Handling**: Passes the ViewModel’s action handler (handles user actions like clicking buttons).
        onAction = {action ->
                when(action) {
                    is RegisterAction.OnLoginClick -> onSignInClick()
                    else -> Unit
                }
                viewModel.onAction(action)

        }
    )
}

// **RegisterScreen Composable**: The primary UI component for the registration screen.
@Composable
private fun RegisterScreen(
    state: RegisterState, // **State Parameter**: Receives the current state of the registration screen from the ViewModel.
    onAction: (RegisterAction) -> Unit // **Action Callback**: A function that handles user actions like clicking buttons or links.
) {
    // **GradientBackground Composable**: Applies a gradient background to the entire screen.
    GradientBackground {
        // **Column Layout**: Arranges the screen elements vertically.
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState()) // **Vertical Scroll**: Enables scrolling for the screen content.
                .fillMaxSize() // **Fill Max Size**: Ensures the column takes up the full screen size.
                .padding(horizontal = 16.dp) // **Horizontal Padding**: Adds padding on the left and right sides of the screen.
                .padding(vertical = 32.dp) // **Vertical Padding**: Adds padding at the top and bottom of the screen.
                .padding(top = 16.dp) // **Top Padding**: Adds additional padding at the top of the screen.
        ) {
            // **Title Text**: Displays the "Create Account" title on the registration screen.
            Text(
                text = stringResource(id = R.string.create_account), // **Text Resource**: Fetches the "Create Account" string from resources.
                style = MaterialTheme.typography.headlineLarge, // **Text Style**: Applies the headline style from the theme.
                color = PacePalWhite // **Text Color**: Sets the text color to white, matching the app's design.
            )
            // **Annotated String**: Builds a styled string with clickable text.
            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = Poppins,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    append(stringResource(id = R.string.already_have_an_account) + " ")
                    pushStringAnnotation(
                        tag = "clickable_text",
                        annotation = stringResource(id = R.string.login)
                    )
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            fontFamily = Poppins
                        )
                    ) {
                        append(stringResource(id = R.string.login))
                    }
                }
            }
            ClickableText(
                text = annotatedString,
                onClick = { offset ->
                    annotatedString.getStringAnnotations(
                        tag = "clickable_text",
                        start = offset,
                        end = offset
                    ).firstOrNull()?.let {
                        onAction(RegisterAction.OnLoginClick)
                    }
                }
            )
            VerticalSpacer(height = 48)

            // **PacePalTextField Composable**: A custom text field for email input.
            PacePalTextField(
                state = state.email, // **State Parameter**: Provides the current state of the email text field.
                startIcon = EmailIcon, // **Start Icon**: Displays an email icon at the start of the text field.
                endIcon = if (state.isEmailValid) CheckIcon else null, // **End Icon**: Shows a check icon if the email is valid, otherwise no icon.
                hint = stringResource(id = R.string.example_email), // **Hint Text**: Provides a hint for what to enter in the text field.
                title = stringResource(id = R.string.email), // **Title Text**: Labels the text field as "Email."
                modifier = Modifier.fillMaxWidth(), // **Modifier**: Makes the text field occupy the full width of its container.
                additionalInfo = stringResource(id = R.string.enter_valid_email), // **Additional Info**: Shows additional guidance if the email is not valid.
                keyboardType = KeyboardType.Email // **Keyboard Type**: Configures the keyboard to show email-specific characters.
            )

// **Spacer Composable**: Adds vertical space between the email and password fields.
            Spacer(modifier = Modifier.height(16.dp))

// **PacePalPasswordTextField Composable**: A custom text field for password input.
            PacePalPasswordTextField(
                state = state.password, // **State Parameter**: Provides the current state of the password text field.
                isPasswordVisible = state.isPasswordVisible, // **Password Visibility**: Indicates whether the password should be visible.
                onTogglePasswordVisibility = {
                    onAction(RegisterAction.OnTogglePasswordVisibilityClick) // **Password Toggle Action**: Calls a function to toggle password visibility.
                },
                hint = stringResource(id = R.string.password), // **Hint Text**: Provides a hint for what to enter in the text field.
                title = stringResource(id = R.string.password), // **Title Text**: Labels the text field as "Password."
                modifier = Modifier.fillMaxWidth() // **Modifier**: Makes the text field occupy the full width of its container.
            )

            Spacer(modifier = Modifier.height(8.dp))

            // **PasswordRequirement for Minimum Length**: Checks if the password meets the minimum length requirement.
            PasswordRequirement(
                text = stringResource(
                    id = R.string.at_least_x_characters, // **Text Resource**: Provides the requirement text from resources.
                    UserDataValidator.MIN_PASSWORD_LENGTH // **Minimum Length**: Inserts the minimum password length into the text.
                ),
                isValid = state.passwordValidationState.hasMinLength // **Validation Check**: Indicates whether the password meets the minimum length.
            )

// **Spacer Composable**: Adds vertical space between password requirements.
            Spacer(modifier = Modifier.height(4.dp))

// **PasswordRequirement for Number Inclusion**: Checks if the password includes at least one number.
            PasswordRequirement(
                text = stringResource(
                    id = R.string.at_least_one_number, // **Text Resource**: Provides the requirement text from resources.
                ),
                isValid = state.passwordValidationState.hasNumber // **Validation Check**: Indicates whether the password includes a number.
            )

// **Spacer Composable**: Adds vertical space between password requirements.
            Spacer(modifier = Modifier.height(4.dp))

// **PasswordRequirement for Lowercase Inclusion**: Checks if the password includes a lowercase letter.
            PasswordRequirement(
                text = stringResource(
                    id = R.string.contains_lowercase_char, // **Text Resource**: Provides the requirement text from resources.
                ),
                isValid = state.passwordValidationState.hasLowercase // **Validation Check**: Indicates whether the password includes a lowercase letter.
            )

// **Spacer Composable**: Adds vertical space between password requirements.
            Spacer(modifier = Modifier.height(4.dp))

// **PasswordRequirement for Uppercase Inclusion**: Checks if the password includes an uppercase letter.
            PasswordRequirement(
                text = stringResource(
                    id = R.string.contains_uppercase_char, // **Text Resource**: Provides the requirement text from resources.
                ),
                isValid = state.passwordValidationState.hasUppercase // **Validation Check**: Indicates whether the password includes an uppercase letter.
            )

// **Spacer Composable**: Adds vertical space before the register button.
            Spacer(modifier = Modifier.height(32.dp))

// **PacePalActionButton Composable**: The button that triggers the registration process.
            PacePalActionButton(
                text = stringResource(id = R.string.register), // **Button Text**: Sets the button label to "Register."
                isLoading = state.isRegistering, // **Loading State**: Indicates whether the registration is in progress.
                enabled = state.canRegister, // **Button Enablement**: Enables the button if all registration criteria are met.
                modifier = Modifier.fillMaxWidth(), // **Modifier**: Makes the button occupy the full width of its container.
                onClick = {
                    onAction(RegisterAction.OnRegisterClick) // **Button Click Action**: Triggers the registration process when clicked.
                }
            )
        }
    }
}

// **PasswordRequirement Composable**: Displays a password requirement with an icon indicating whether it is met.
@Composable
fun PasswordRequirement(
    text: String, // **Text Parameter**: The descriptive text of the password requirement.
    isValid: Boolean, // **Validation Status**: Indicates whether the requirement is met.
    modifier: Modifier = Modifier // **Modifier Parameter**: Allows customization of the composable’s layout and appearance.
) {
    // **Row Layout**: Aligns the icon and text horizontally.
    Row(
        modifier = modifier, // **Modifier**: Applies any external styling or layout instructions.
        verticalAlignment = Alignment.CenterVertically // **Vertical Alignment**: Aligns the icon and text vertically at the center.
    ) {
        // **Icon Display**: Shows a check icon if the requirement is met, otherwise a cross icon.
        Icon(
            imageVector = if (isValid) {
                CheckIcon // **Check Icon**: Indicates the requirement is met.
            } else {
                CrossIcon // **Cross Icon**: Indicates the requirement is not met.
            },
            contentDescription = null, // **Content Description**: No description provided for accessibility purposes.
            tint = if(isValid) PacePalBlue else PacePalDarkRed // **Tint**: Colors the icon blue if valid, red if invalid.
        )
        // **Spacer Composable**: Adds horizontal space between the icon and text.
        Spacer(modifier = Modifier.width(16.dp))
        // **Text Composable**: Displays the password requirement description.
        Text(
            text = text, // **Text**: The password requirement description.
            color = MaterialTheme.colorScheme.onSurfaceVariant, // **Text Color**: Uses the appropriate color from the theme.
            fontSize = 14.sp // **Font Size**: Sets the font size to 14 sp for readability.
        )
    }
}



@Preview(showBackground = true)
@Composable
private fun RegisterScreenRootScreenPreview() {
    PacePalTheme {
        RegisterScreen(
            state = RegisterState(
                passwordValidationState = PasswordValidationState(
                    hasMinLength = true,
                    hasUppercase = true,
                    hasLowercase = true,
                    hasNumber = true
                )
            ),
            onAction = {}
        )
    }
}

