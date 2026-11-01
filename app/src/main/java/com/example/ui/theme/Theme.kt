package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = SecondarySlate,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    tertiary = TertiaryAmber,
    onTertiary = OnPrimary,
    tertiaryContainer = TertiaryContainer,
    background = SurfaceCanvas,
    onBackground = TextPrimary,
    surface = SurfaceCanvas,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceContainer,
    onSurfaceVariant = TextSecondary,
    surfaceContainerLowest = SurfaceCard,
    surfaceContainerLow = SurfaceContainerLow,
    surfaceContainer = SurfaceContainer,
    surfaceContainerHigh = SurfaceContainerHigh,
    outline = OutlineColor,
    outlineVariant = OutlineVariantColor,
    error = ErrorDestructive,
    errorContainer = ErrorContainer
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryGreenLight,
    onPrimary = OnPrimaryContainer,
    primaryContainer = PrimaryGreenDark,
    onPrimaryContainer = PrimaryContainer,
    secondary = SecondaryContainer,
    onSecondary = OnSecondaryContainer,
    background = SurfaceDark,
    onBackground = OnPrimary,
    surface = SurfaceDark,
    onSurface = OnPrimary,
    surfaceVariant = SurfaceCardDark,
    onSurfaceVariant = SecondaryContainer,
    outline = OutlineVariantColor,
    error = ErrorDestructive
)

@Composable
fun LearnovaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window ?: return@SideEffect
            window.statusBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

// Keep alias for compatibility with template
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) = LearnovaTheme(darkTheme = darkTheme, content = content)
