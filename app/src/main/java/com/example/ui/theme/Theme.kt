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
    primary = EspressoPrimary,
    onPrimary = IvoryPaper,
    primaryContainer = CaramelTan,
    onPrimaryContainer = EspressoDark,
    secondary = SoftBrown,
    onSecondary = IvoryPaper,
    secondaryContainer = WarmSand,
    onSecondaryContainer = EspressoDark,
    tertiary = WarmChocolate,
    onTertiary = IvoryPaper,
    background = CreamBackground,
    onBackground = TextPrimaryDark,
    surface = CreamSurface,
    onSurface = TextPrimaryDark,
    surfaceVariant = CreamSurfaceVariant,
    onSurfaceVariant = TextSecondaryBrown,
    outline = CaramelTan,
    error = ErrorRed,
    onError = IvoryPaper
)

private val DarkColorScheme = darkColorScheme(
    primary = WarmSand,
    onPrimary = EspressoDark,
    primaryContainer = EspressoPrimary,
    onPrimaryContainer = IvoryPaper,
    secondary = CaramelTan,
    onSecondary = EspressoDark,
    secondaryContainer = WarmChocolate,
    onSecondaryContainer = IvoryPaper,
    tertiary = SoftBrown,
    onTertiary = IvoryPaper,
    background = EspressoDark,
    onBackground = CreamBackground,
    surface = EspressoSurface,
    onSurface = CreamBackground,
    surfaceVariant = EspressoPrimary,
    onSurfaceVariant = WarmSand,
    outline = SoftBrown,
    error = ErrorRed,
    onError = IvoryPaper
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // We intentionally prioritize our bespoke luxury cream/espresso palette
    // over generic dynamic colors to maintain the campus binder physical identity.
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            window?.let {
                WindowCompat.getInsetsController(it, view).isAppearanceLightStatusBars = !darkTheme
                WindowCompat.getInsetsController(it, view).isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
