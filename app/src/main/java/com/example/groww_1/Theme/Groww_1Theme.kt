package com.example.groww_1.Theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.groww_1.ThemeViewModel

private val LightColors = lightColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFF0D47A1),
    background = androidx.compose.ui.graphics.Color(0xFFFFFFFF),
    onPrimary = androidx.compose.ui.graphics.Color.White
)

private val DarkColors = darkColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFF90CAF9),
    background = androidx.compose.ui.graphics.Color(0xFF131334),
    onPrimary = androidx.compose.ui.graphics.Color.White
)

@Composable
fun Groww_1Theme(
    themeViewModel: ThemeViewModel,
    content: @Composable () -> Unit
) {
    val isDarkMode by themeViewModel.isDarkMode.collectAsState()

    MaterialTheme(
        colorScheme = if (isDarkMode) DarkColors else LightColors,
        typography = MaterialTheme.typography,
        content = content
    )
}
