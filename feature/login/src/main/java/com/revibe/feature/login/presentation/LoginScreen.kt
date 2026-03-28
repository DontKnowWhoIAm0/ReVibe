package com.revibe.feature.login.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.revibe.core.R as CoreR
import com.revibe.feature.login.R

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {}
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    val buttonGradient = Brush.horizontalGradient(
        colors = listOf(colors.primary, colors.primary.copy(alpha = 0.8f))
    )

    Scaffold(
        containerColor = colors.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
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
                text = stringResource(R.string.login_title),
                style = typography.headlineLarge,
                color = colors.onBackground
            )

            Spacer(modifier = Modifier.height(40.dp))

            LoginTextField(
                placeholder = stringResource(R.string.login_email_placeholder)
            )
            Spacer(modifier = Modifier.height(12.dp))

            LoginTextField(
                placeholder = stringResource(R.string.login_password_placeholder),
                isPassword = true
            )

            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(buttonGradient)
                    .clickable { onLoginClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.login_button),
                    color = colors.onPrimary,
                    style = typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Text(
                    text = stringResource(R.string.login_no_account),
                    color = colors.onBackground.copy(alpha = 0.6f),
                    style = typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.login_register),
                    color = colors.primary,
                    style = typography.bodyMedium,
                    modifier = Modifier.clickable { onRegisterClick() }
                )
            }
        }
    }
}

@Composable
private fun LoginTextField(
    placeholder: String,
    isPassword: Boolean = false
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        placeholder = {
            Text(
                placeholder,
                style = typography.bodyMedium,
                color = colors.onSurfaceVariant
            )
        },
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(6.dp)),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
            unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
            focusedContainerColor = colors.surface,
            unfocusedContainerColor = colors.surface,
            cursorColor = colors.primary
        )
    )
}
