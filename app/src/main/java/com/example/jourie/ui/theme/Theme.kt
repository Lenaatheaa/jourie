package com.example.jourie.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
// 🌙 DARK COLOR SCHEME
// ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
private val DarkColorScheme =
        darkColorScheme(
                primary = Purple400, // ⭐ Main brand color
                secondary = Purple500, // Gradient pair
                tertiary = Pink80,
                background = Color(0xFF1C1B1F), // Dark background
                surface = Color(0xFF1C1B1F), // Dark surface
                onPrimary = White, // Text on purple
                onSecondary = White,
                onTertiary = White,
                onBackground = Color(0xFFE6E1E5), // Light text on dark bg
                onSurface = Color(0xFFE6E1E5),
        )

// ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
// ☀️ LIGHT COLOR SCHEME (Default)
// ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
private val LightColorScheme =
        lightColorScheme(
                primary = Purple400, // ⭐ Main brand color (#C084FC)
                secondary = Purple500, // Gradient pair (#A855F7)
                tertiary = Pink40,
                background = White, // Clean white background
                surface = White, // Clean white surface
                onPrimary = White, // White text on purple buttons
                onSecondary = White,
                onTertiary = White,
                onBackground = Gray900, // Dark text on white background
                onSurface = Gray700, // Body text on white surface
        )

@Composable
fun JourieTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        dynamicColor: Boolean = false, // Dinonaktifkan agar warna ungu utama kita selalu aktif
        content: @Composable () -> Unit
) {
        val colorScheme =
                when {
                        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                                val context = LocalContext.current
                                if (darkTheme) dynamicDarkColorScheme(context)
                                else dynamicLightColorScheme(context)
                        }
                        darkTheme -> DarkColorScheme
                        else -> LightColorScheme
                }

        val view = LocalView.current
        if (!view.isInEditMode) {
                SideEffect {
                        val window = (view.context as Activity).window
                        window.statusBarColor = Color.Transparent.toArgb() // Status bar transparan
                        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                                !darkTheme
                }
        }

        MaterialTheme(
                colorScheme = colorScheme,
                typography = Typography,
                shapes = Shapes,
                content = content
        )
}
