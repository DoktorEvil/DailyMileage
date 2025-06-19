package com.example.dailymileageapp.ui.theme // Ensure this package matches your project

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color // Make sure to import Color

// Re-using the color definitions you previously had.
// If you have a separate Color.kt, these would typically be defined there
// and then potentially used to construct LightColorScheme/DarkColorScheme here or directly.
private val Purple500 = Color(0xFF6200EE)
private val Purple200 = Color(0xFFBB86FC)
private val Teal200 = Color(0xFF03DAC6)
private val White = Color(0xFFFFFFFF)
private val Black = Color(0xFF000000)
private val DarkGrey = Color(0xFF121212)

// Define your light and dark color palettes
// These are simplified examples. For a full Material 3 theme, you'd define more roles
// (primaryContainer, onPrimaryContainer, secondaryContainer, etc.)
private val LightColorScheme = lightColorScheme(
    primary = Purple500,
    secondary = Teal200,
    tertiary = Teal200, // Often different from secondary in M3
    background = White,
    surface = White,
    onPrimary = White,
    onSecondary = Black,
    onTertiary = Black,
    onBackground = Black,
    onSurface = Black
    // You can define other colors as well like error, surfaceVariant, outline etc.
)

private val DarkColorScheme = darkColorScheme(
    primary = Purple200,
    secondary = Teal200,
    tertiary = Teal200,
    background = DarkGrey,
    surface = DarkGrey,
    onPrimary = Black,
    onSecondary = Black,
    onTertiary = Black,
    onBackground = White,
    onSurface = White
    // You can define other colors as well
)

@Composable
fun DailyMileageAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    // dynamicColor: Boolean = true, // Uncomment if you want to support dynamic colors
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> { // Uncomment for dynamic color
        // val context = LocalContext.current
        // if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        // }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // If you add dynamicColor, you'll also need these imports:
    // import android.os.Build
    // import androidx.compose.material3.dynamicDarkColorScheme
    // import androidx.compose.material3.dynamicLightColorScheme
    // import androidx.compose.ui.platform.LocalContext

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Assumes Typography is defined in Typography.kt (or this file)
        shapes = Shapes,         // Assumes Shapes is defined in Shapes.kt (or this file)
        content = content
    )
}