@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")
@file:OptIn(ExperimentalFoundationApi::class)

package com.nolawiworkineh.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nolawiworkineh.auth.domain.PasswordValidationState
import com.nolawiworkineh.core.presentation.designsystem.PacePalTheme
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

}


@Preview(showBackground = true)
@Composable
private fun RegisterScreenRootScreenPreview() {
    PacePalTheme {
        RegisterScreen(
            state =  RegisterState(
                passwordValidationState = PasswordValidationState(
                    hasNumber = true,
                )
            ),
            onAction = {}
        )
    }
}