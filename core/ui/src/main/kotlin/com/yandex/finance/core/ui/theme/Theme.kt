package com.yandex.finance.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


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

private val DarkColorScheme = darkColorScheme(
    error = ColorError,
    onError = ColorOnError,
    surface = ColorSurfaceDark,
    primary = ColorPrimary,
    tertiary = ColorTertiary,
    background = ColorSurfaceDark,
    onSurface = ColorOnSurfaceDark,
    onPrimary = ColorOnSurfaceDark,
    secondary = ColorSecondary,
    onSecondary = ColorOnSurfaceDark,
    onBackground = ColorOnSurfaceDark,
    surfaceVariant = ColorSurfaceVariantDark,
    outlineVariant = ColorOutlineVariant,
    surfaceContainer = ColorSurfaceContainerDark,
    surfaceContainerHigh = ColorSurfaceContainerHighDark,
)

private fun getColorByName(name: String?): Color = when (name) {
    "Blue" -> Color(0xFF2196F3)
    "Green" -> ColorPrimary
    "Red" -> Color(0xFFF44336)
    "Purple" -> Color(0xFF9C27B0)
    "Orange" -> Color(0xFFFF9800)
    else -> ColorPrimary
}

@Composable
fun YandexFinanceTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    mainColorName: String? = null,
    content: @Composable () -> Unit
) {
    val mainColor = getColorByName(mainColorName)
    val colorScheme = when {
        darkTheme -> DarkColorScheme.copy(
            primary = mainColor,
            secondary = if (mainColor == ColorPrimary) ColorSecondary
                else mainColor.copy(alpha = .6f)
        )

        else -> LightColorScheme.copy(
            primary = mainColor,
            secondary = if (mainColor == ColorPrimary) ColorSecondary
            else mainColor.copy(alpha = .6f)
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
