package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val GamingDarkColorScheme = darkColorScheme(
    primary = CrimsonRed,
    onPrimary = TextPrimary,
    primaryContainer = Color(0xFF3F0A14),
    onPrimaryContainer = Color(0xFFFFCDD2),
    secondary = NeonCyan,
    onSecondary = DarkSurface,
    secondaryContainer = Color(0xFF00363D),
    onSecondaryContainer = Color(0xFFE0F7FA),
    tertiary = NeonGold,
    onTertiary = DarkSurface,
    background = DarkSurface,
    onBackground = TextPrimary,
    surface = DarkCard,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = TextSecondary,
    outline = DarkCardBorder
)

@Composable
fun FireHUDTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = GamingDarkColorScheme,
        typography = Typography,
        content = content
    )
}
