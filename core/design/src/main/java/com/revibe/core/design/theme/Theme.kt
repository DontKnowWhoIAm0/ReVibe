package com.revibe.core.design.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

private val RevibeLightColors = lightColorScheme(
    background = LightBackground,
    surface = LightSurface,
    primary = LightPrimary,
    secondary = LightSecondary,
    onBackground = LightTextMain,
    onSurface = LightTextMain,
)

private val RevibeDarkColors = darkColorScheme(
    background = DarkBackground,
    surface = DarkSurface,
    primary = DarkPrimary,
    secondary = DarkSecondary,
    onBackground = DarkTextMain,
    onSurface = DarkTextMain,
)

@Composable
fun RevibeTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) RevibeDarkColors else RevibeLightColors,
        typography = RevibeTypography,
        content = content
    )
}
