package com.packagift.app.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val PackAGiftColorScheme = lightColorScheme(
    primary = PinkPrimary,
    onPrimary = WineSecondary,
    primaryContainer = PinkPrimary,
    onPrimaryContainer = WineSecondary,
    secondary = WineSecondary,
    onSecondary = SurfaceWhite,
    secondaryContainer = BlueLight,
    onSecondaryContainer = WineSecondary,
    tertiary = BlueDark,
    onTertiary = SurfaceWhite,
    tertiaryContainer = BlueLight,
    onTertiaryContainer = BlueDark,
    background = SurfaceWhite,
    onBackground = TextPrimary,
    surface = SurfaceWhite,
    onSurface = TextPrimary,
    surfaceVariant = BlueLight,
    onSurfaceVariant = TextSecondary,
    outline = BlueMid
)

@Composable
fun PackAGiftTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = PackAGiftColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        val window = (view.context as Activity).window
        window.statusBarColor = SurfaceWhite.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
