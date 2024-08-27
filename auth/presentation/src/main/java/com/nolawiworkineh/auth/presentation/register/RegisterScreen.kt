@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")
@file:OptIn(ExperimentalFoundationApi::class)

package com.nolawiworkineh.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nolawiworkineh.auth.domain.PasswordValidationState
import com.nolawiworkineh.auth.presentation.R
import com.nolawiworkineh.core.presentation.designsystem.PacePalGray
import com.nolawiworkineh.core.presentation.designsystem.PacePalTheme
import com.nolawiworkineh.core.presentation.designsystem.PacePalWhite
import com.nolawiworkineh.core.presentation.designsystem.Poppins
import com.nolawiworkineh.core.presentation.designsystem.components.GradientBackground
import com.nolawiworkineh.presentation.ui.VerticalSpacer
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    viewModel: RegisterViewModel = koinViewModel(),
    onSignInClick: () -> Unit,
    onSuccessfulRegistration: () -> Unit
) {
    RegisterScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable

private fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {
    GradientBackground {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 32.dp)
                .padding(top = 48.dp)
        ) {
            Text(
                text = stringResource(id = R.string.create_account),
                style = MaterialTheme.typography.headlineLarge,
                color = PacePalWhite
            )
            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = Poppins,
                        color = PacePalGray
                    )
                ) {
                    append(stringResource(id = R.string.already_have_an_account) + " ")
                    pushStringAnnotation(
                        tag = "clickable_text",
                        annotation = stringResource(id = R.string.login)
                    )
                    withStyle(
                        style = SpanStyle(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            
                        )
                    ) {
                        append(stringResource(id = R.string.login))
                    }
                }
            }
            ClickableText(text = annotatedString,
                onClick = {
                    annotatedString.getStringAnnotations(
                        tag = "clickable_text",
                        start = it,
                        end = it
                    ).firstOrNull()?.let {
                        onAction(RegisterAction.OnLoginClick)
                    }
                }
            )
            VerticalSpacer(48)


        }
    }
}


@Preview(showBackground = true)
@Composable
private fun RegisterScreenRootScreenPreview() {
    PacePalTheme {
        RegisterScreen(
            state = RegisterState(
                passwordValidationState = PasswordValidationState(
                    hasNumber = true,
                )
            ),
            onAction = {}
        )
    }
}