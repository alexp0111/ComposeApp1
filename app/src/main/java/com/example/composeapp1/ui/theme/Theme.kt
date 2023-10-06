package com.example.composeapp1.ui.theme

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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val DarkColorScheme = darkColorScheme(
    primary = PCDark,
    secondary = SCDark,
    tertiary = TCDark,
    onPrimary = OPCDark,
    primaryContainer = PCCDark,
    onPrimaryContainer = OPCCDark,
    onSecondary = OSCDark,
    secondaryContainer = SCCDark,
    onSecondaryContainer = OSCCDark,
    onTertiary = OTCDark,
    tertiaryContainer = TCCDark,
    onTertiaryContainer = OTCCDark,
    error = ECDark,
    errorContainer = OECDark,
    onError = ECCDark,
    onErrorContainer = OECCDark,
    background = BCDark,
    onBackground = OBCDark,
    surface = SurfCDark,
    onSurface = OSurfDark,
    outline = OUTLCDark,
    surfaceVariant = SurfVCDark,
    onSurfaceVariant = OSurfVCDark
)

private val LightColorScheme = lightColorScheme(
    primary = PCLight,
    secondary = SCLight,
    tertiary = TCLight,
    onPrimary = OPCLight,
    primaryContainer = PCCLight,
    onPrimaryContainer = OPCCLight,
    onSecondary = OSCLight,
    secondaryContainer = SCCLight,
    onSecondaryContainer = OSCCLight,
    onTertiary = OTCLight,
    tertiaryContainer = TCCLight,
    onTertiaryContainer = OTCCLight,
    error = ECLight,
    errorContainer = ECCLight,
    onError = OECLight,
    onErrorContainer = OECCLight,
    background = BCLight,
    onBackground = OBCLight,
    surface = SurfCLight,
    onSurface = OSurfCLight,
    outline = OUTLCLight,
    surfaceVariant = SurfVCLight,
    onSurfaceVariant = OSurfVCLight
)

@Composable
fun ComposeApp1Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}