@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")
@file:OptIn(ExperimentalFoundationApi::class)

package com.nolawiworkineh.auth.presentation.login

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
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
import com.nolawiworkineh.auth.presentation.R
import com.nolawiworkineh.core.presentation.designsystem.EmailIcon
import com.nolawiworkineh.core.presentation.designsystem.PacePalTheme
import com.nolawiworkineh.core.presentation.designsystem.Poppins
import com.nolawiworkineh.core.presentation.designsystem.VerticalSpacer
import com.nolawiworkineh.core.presentation.designsystem.components.GradientBackground
import com.nolawiworkineh.core.presentation.designsystem.components.PacePalActionButton
import com.nolawiworkineh.core.presentation.designsystem.components.PacePalPasswordTextField
import com.nolawiworkineh.core.presentation.designsystem.components.PacePalTextField
import com.nolawiworkineh.presentation.ui.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable

fun LoginScreenRoot(
    viewModel: LoginViewModel = koinViewModel(),
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit

) {
    // **Current Context**: Retrieves the current context to display Toast messages or interact with the system.
    val context = LocalContext.current
    // **Keyboard Controller**: Manages the software keyboard, allowing the app to show or hide the keyboard.
    val keyboardController = LocalSoftwareKeyboardController.current
    // **Observing Events from ViewModel**: Listens to events emitted by the ViewModel and responds (like showing errors or success).
    ObserveAsEvents(flow = viewModel.events) { event ->
        when (event) {
            // **Error Event**: Handles errors like invalid email, server issues, etc.
            is LoginEvent.Error -> {
                keyboardController?.hide()  // **Hide Keyboard**: Hides the keyboard when an error occurs.
                // **Show Error Message**: Displays the error as a Toast.
                Toast.makeText(context, event.errorMessage.asString(context), Toast.LENGTH_LONG).show()
            }
            // **Success Event**: Handles successful registration.
            LoginEvent.LoginSuccess -> {
                keyboardController?.hide()  // **Hide Keyboard**: Hides the keyboard upon success.
                // **Show Success Message**: Displays a success message.
                Toast.makeText(context, R.string.your_logged_in, Toast.LENGTH_LONG).show()
                // **Trigger Success Callback**: Calls the provided function after successful registration (e.g., navigate to another screen).
                onLoginSuccess()
            }
        }
    }
    LoginScreen(
        state = viewModel.state,
        onAction = { action ->
            when(action) {
                is LoginAction.onRegisterClick -> onSignUpClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {
    GradientBackground {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp) // **Horizontal Padding**: Adds padding on the left and right sides of the screen.
                .padding(vertical = 32.dp) // **Vertical Padding**: Adds padding at the top and bottom of the screen.
                .padding(top = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Hi There text
                Text(
                    text = stringResource(id = R.string.hi_there),
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.headlineMedium
                )

                // welcome text
                Text(
                    text = stringResource(id = R.string.welcome_text),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                VerticalSpacer(48)

                PacePalTextField(
                    state = state.email,
                    startIcon = EmailIcon,
                    endIcon = null,
                    keyboardType = KeyboardType.Email,
                    hint = stringResource(id = R.string.example_email),
                    title = stringResource(id = R.string.email),
                    modifier = Modifier.fillMaxWidth()
                )

                VerticalSpacer(16)

                PacePalPasswordTextField(
                    state = state.password,
                    isPasswordVisible = state.isPasswordVisible,
                    onTogglePasswordVisibility = {
                        onAction(LoginAction.onTogglePasswordVisibility)
                    },
                    hint = stringResource(id = R.string.password),
                    title = stringResource(id = R.string.password),
                    modifier = Modifier.fillMaxWidth()
                )

                VerticalSpacer(32)

                PacePalActionButton(
                    text = stringResource(id = R.string.login),
                    isLoading = state.isLoginInProgress,
                    enabled = state.canLogin && !state.isLoginInProgress,
                    onClick = {
                        onAction(LoginAction.onLoginClick)
                    },
                )

                Spacer(modifier = Modifier.weight(1f)) // Spacer to push content to bottom
            }

            // Annotated clickable text at the bottom
            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = Poppins,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    append(stringResource(id = R.string.dont_have_an_account) + " ")
                    pushStringAnnotation(
                        tag = "clickable_text",
                        annotation = stringResource(id = R.string.sign_up)
                    )
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            fontFamily = Poppins
                        )
                    ) {
                        append(stringResource(id = R.string.sign_up))
                    }
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            ) {
                ClickableText(
                    text = annotatedString,
                    onClick = { offset ->
                        annotatedString.getStringAnnotations(
                            tag = "clickable_text",
                            start = offset,
                            end = offset
                        ).firstOrNull()?.let {
                            onAction(LoginAction.onRegisterClick)
                        }
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    PacePalTheme {
        LoginScreen(
            state = LoginState(),
            onAction = {}
        )
    }
}














