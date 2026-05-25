package com.revibe.feature.registration.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.revibe.core.ui.R as CoreR
import com.revibe.feature.registration.R
import com.revibe.feature.registration.presentation.RegistrationViewModel
import com.revibe.core.ui.components.ReVibeTextField

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel,
    onLoginClick: () -> Unit = {},
    onRegistrationSuccess: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    val buttonGradient = Brush.horizontalGradient(colors = listOf(colors.primary, colors.primary.copy(alpha = 0.8f)))

    LaunchedEffect(state.success) {
        if (state.success) { onRegistrationSuccess() }
    }

    Scaffold(containerColor = colors.background) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(70.dp))

            Image(
                painter = painterResource(id = CoreR.drawable.light_logo),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.registration_title),
                style = typography.headlineLarge,
                color = colors.onBackground
            )

            Spacer(modifier = Modifier.height(40.dp))

            ReVibeTextField(
                value = state.fullName,
                onValueChange = viewModel::onFullNameChange,
                placeholder = stringResource(R.string.registration_name_placeholder),
                isError = state.fullNameError != null,
                errorMessage = state.fullNameError,
                imeAction = ImeAction.Next
            )

            Spacer(modifier = Modifier.height(12.dp))

            ReVibeTextField(
                value = state.email,
                onValueChange = viewModel::onEmailChange,
                placeholder = stringResource(R.string.registration_email_placeholder),
                keyboardType = KeyboardType.Email,
                isError = state.emailError != null,
                errorMessage = state.emailError,
                imeAction = ImeAction.Next
            )

            Spacer(modifier = Modifier.height(12.dp))

            ReVibeTextField(
                value = state.password,
                onValueChange = viewModel::onPasswordChange,
                placeholder = stringResource(R.string.registration_password_placeholder),
                isPassword = true,
                passwordVisible = state.isPasswordVisible,
                onPasswordToggle = viewModel::onTogglePasswordVisibility,
                isError = state.passwordError != null,
                errorMessage = state.passwordError,
                imeAction = ImeAction.Next
            )
            Spacer(modifier = Modifier.height(12.dp))

            ReVibeTextField(
                value = state.confirmPassword,
                onValueChange = viewModel::onConfirmPasswordChange,
                placeholder = stringResource(R.string.registration_confirm_password_placeholder),
                isPassword = true,
                passwordVisible = state.isConfirmPasswordVisible,
                onPasswordToggle = viewModel::onToggleConfirmPasswordVisibility,
                isError = state.confirmPasswordError != null,
                errorMessage = state.confirmPasswordError,
                imeAction = ImeAction.Done
            )

            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(buttonGradient)
                    .clickable(enabled = !state.isLoading) { viewModel.register() },
                contentAlignment = Alignment.Center
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(color = colors.onPrimary, strokeWidth = 2.dp, modifier = Modifier.size(24.dp))
                } else {
                    Text(
                        text = stringResource(R.string.registration_button),
                        color = colors.onPrimary,
                        style = typography.bodyLarge
                    )
                }
            }

            state.errorMessage?.let { msg ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(msg, color = MaterialTheme.colorScheme.error, style = typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.registration_have_account),
                    color = colors.onBackground.copy(alpha = 0.6f),
                    style = typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.registration_login),
                    color = colors.primary,
                    style = typography.bodyMedium,
                    modifier = Modifier.clickable { onLoginClick() }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}