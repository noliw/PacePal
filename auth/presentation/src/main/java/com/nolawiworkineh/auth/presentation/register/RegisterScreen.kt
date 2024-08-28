@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")
@file:OptIn(ExperimentalFoundationApi::class)

package com.nolawiworkineh.auth.presentation.register

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
import com.nolawiworkineh.core.presentation.designsystem.PacePalGray
import com.nolawiworkineh.core.presentation.designsystem.PacePalTheme
import com.nolawiworkineh.core.presentation.designsystem.PacePalWhite
import com.nolawiworkineh.core.presentation.designsystem.Poppins
import com.nolawiworkineh.core.presentation.designsystem.VerticalSpacer
import com.nolawiworkineh.core.presentation.designsystem.components.GradientBackground
import com.nolawiworkineh.core.presentation.designsystem.components.PacePalActionButton
import com.nolawiworkineh.core.presentation.designsystem.components.PacePalPasswordTextField
import com.nolawiworkineh.core.presentation.designsystem.components.PacePalTextField
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
    // **RegisterScreen Composable**: The main UI composable for the registration screen.
    RegisterScreen(
        // **State Propagation**: Passes the current state of the registration screen from the ViewModel.
        state = viewModel.state,
        // **Action Handling**: Passes the ViewModel's action handler to the UI.
        onAction = viewModel::onAction
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
                .padding(top = 48.dp) // **Top Padding**: Adds additional padding at the top of the screen.
        ) {
            // **Title Text**: Displays the "Create Account" title on the registration screen.
            Text(
                text = stringResource(id = R.string.create_account), // **Text Resource**: Fetches the "Create Account" string from resources.
                style = MaterialTheme.typography.headlineLarge, // **Text Style**: Applies the headline style from the theme.
                color = PacePalWhite // **Text Color**: Sets the text color to white, matching the app's design.
            )
            // **Annotated String**: Builds a styled string with clickable text.
            val annotatedString = buildAnnotatedString {
                // **Span Style**: Applies a style to the "Already have an account?" text.
                withStyle(
                    style = SpanStyle(
                        fontFamily = Poppins, // **Font Family**: Uses the Poppins font for the text.
                        color = PacePalGray // **Text Color**: Sets the color of the text to gray.
                    )
                ) {
                    append(stringResource(id = R.string.already_have_an_account) + " ") // **Append Text**: Adds the "Already have an account?" text.
                    // **Clickable Text Annotation**: Marks the "Login" text as clickable.
                    pushStringAnnotation(
                        tag = "clickable_text", // **Tag**: Identifies the clickable text annotation.
                        annotation = stringResource(id = R.string.login) // **Annotation**: Associates the "Login" text with the clickable action.
                    )
                    // **Span Style**: Applies a bold style to the "Login" text.
                    withStyle(
                        style = SpanStyle(
                            fontFamily = Poppins, // **Font Family**: Uses the Poppins font for the clickable text.
                            fontWeight = FontWeight.Bold, // **Font Weight**: Makes the "Login" text bold.
                            color = MaterialTheme.colorScheme.primary, // **Text Color**: Sets the color of the text to the primary color.
                        )
                    ) {
                        append(stringResource(id = R.string.login)) // **Append Text**: Adds the "Login" text.
                    }
                }
            }
            // **ClickableText Composable**: Displays the annotated string as clickable text.
            ClickableText(
                text = annotatedString, // **Text**: Passes the annotated string to the ClickableText composable.
                onClick = {
                    // **Handle Click**: Handles the click on the "Login" text.
                    annotatedString.getStringAnnotations(
                        tag = "clickable_text", // **Tag**: Looks for the "clickable_text" annotation.
                        start = it, // **Start Position**: The start of the clicked area.
                        end = it // **End Position**: The end of the clicked area.
                    ).firstOrNull()?.let {
                        // **Trigger Action**: Triggers the OnLoginClick action when the "Login" text is clicked.
                        onAction(RegisterAction.OnLoginClick)
                    }
                }
            )
            VerticalSpacer(height = 60)
            
            PacePalTextField(
                state = state.email,
                startIcon = EmailIcon,
                endIcon = if (state.isEmailValid) CheckIcon else null,
                hint = stringResource(id = R.string.example_email),
                title = stringResource(id = R.string.email),
                modifier = Modifier.fillMaxWidth(),
                additionalInfo = stringResource(id = R.string.must_be_a_valid_email),
                keyboardType = KeyboardType.Email
            )
            Spacer(modifier = Modifier.height(32.dp))
            PacePalPasswordTextField(
                state = state.password,
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibility = {
                    onAction(RegisterAction.OnTogglePasswordVisibilityClick)
                },
                hint = stringResource(id = R.string.password),
                title = stringResource(id = R.string.password),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            PasswordRequirement(
                text = stringResource(
                    id = R.string.at_least_x_characters,
                    UserDataValidator.MIN_PASSWORD_LENGTH
                ),
                isValid = state.passwordValidationState.hasMinLength
            )
            Spacer(modifier = Modifier.height(4.dp))
            PasswordRequirement(
                text = stringResource(
                    id = R.string.at_least_one_number,
                ),
                isValid = state.passwordValidationState.hasNumber
            )
            Spacer(modifier = Modifier.height(4.dp))
            PasswordRequirement(
                text = stringResource(
                    id = R.string.contains_lowercase_char,
                ),
                isValid = state.passwordValidationState.hasLowercase
            )
            Spacer(modifier = Modifier.height(4.dp))
            PasswordRequirement(
                text = stringResource(
                    id = R.string.contains_uppercase_char,
                ),
                isValid = state.passwordValidationState.hasUppercase
            )
            Spacer(modifier = Modifier.height(32.dp))
            PacePalActionButton(
                text = stringResource(id = R.string.register),
                isLoading = state.isRegistering,
                enabled = state.canRegister,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onAction(RegisterAction.OnRegisterClick)
                }
            )
        }
    }
}

@Composable
fun PasswordRequirement(
    text: String,
    isValid: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isValid) {
                CheckIcon
            } else {
                CrossIcon
            },
            contentDescription = null,
            tint = if(isValid) PacePalBlue else PacePalDarkRed
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp
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