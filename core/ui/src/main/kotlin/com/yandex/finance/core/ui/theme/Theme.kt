package com.yandex.finance.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val LightColorScheme = lightColorScheme(
    error = ColorError,
    onError = ColorOnError,
    surface = ColorSurface,
    primary = ColorPrimary,
    tertiary = ColorTertiary,
    background = ColorSurface,
    onSurface = ColorOnSurface,
    onPrimary = ColorOnSurface,
    secondary = ColorSecondary,
    onSecondary = ColorOnSurface,
    onBackground = ColorOnSurface,
    surfaceVariant = ColorSurfaceVariant,
    outlineVariant = ColorOutlineVariant,
    surfaceContainer = ColorSurfaceContainer,
    surfaceContainerHigh = ColorSurfaceContainerHigh,
)

private val DarkColorScheme = LightColorScheme

@Composable
fun YandexFinanceTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
