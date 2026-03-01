package com.example.serviciosmartins.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColors = darkColorScheme(
    primary = SkyBlue,
    secondary = SkyBlueSoft,
    background = BgBlack,
    surface = PanelBlack,
    onPrimary = BgBlack,
    onSecondary = BgBlack,
    onBackground = SkyBlue,
    onSurface = TextSoft
)

@Composable
fun SERVICIOSMARTINSTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColors,
        typography = Typography,
        content = content
    )
}
