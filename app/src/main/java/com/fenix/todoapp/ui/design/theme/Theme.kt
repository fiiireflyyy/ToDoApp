package com.fenix.todoapp.ui.design.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
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

private val DarkColorScheme = darkColorScheme(
    outline = SupportSeparatorDarkColor,
    onPrimary = LabelPrimaryDarkColor,
    onSecondary = LabelSecondaryDarkColor,
    onTertiary = LabelTertiaryDarkColor,
    background = BackPrimaryDarkColor,
    surface = BackPrimaryDarkColor,
)


val ColorScheme.blue: Color
    @Composable
    get() = if (isSystemInDarkTheme()) ColorBlueDarkColor else ColorBlueLightColor

val ColorScheme.red: Color
    @Composable
    get() = if (isSystemInDarkTheme()) ColorRedDarkColor else ColorRedLightColor

val ColorScheme.green: Color
    @Composable
    get() = if (isSystemInDarkTheme()) ColorGreenDarkColor else ColorGreenLightColor

val ColorScheme.gray: Color
    @Composable
    get() = if (isSystemInDarkTheme()) ColorGrayDarkColor else ColorGrayLightColor

val ColorScheme.grayLight: Color
    @Composable
    get() = if (isSystemInDarkTheme()) ColorGrayLightDarkColor else ColorGrayLightLightColor

val ColorScheme.white: Color
    @Composable
    get() = if (isSystemInDarkTheme()) ColorWhiteDarkColor else ColorWhiteLightColor

val ColorScheme.overlay: Color
    @Composable
    get() = if (isSystemInDarkTheme()) SupportOverlayDarkColor else SupportOverlayLightColor

val ColorScheme.disable: Color
    @Composable
    get() = if (isSystemInDarkTheme()) LabelDisableDarkColor else LabelDisableLightColor

val ColorScheme.label: Color
    @Composable
    get() = if (isSystemInDarkTheme()) LabelPrimaryDarkColor else LabelPrimaryLightColor

val ColorScheme.tertiry: Color
    @Composable
    get() = if (isSystemInDarkTheme()) LabelTertiaryDarkColor else LabelTertiaryLightColor

val ColorScheme.lightRed: Color
    @Composable
    get() = if (isSystemInDarkTheme()) LightRedDarkColor else LightRedLigthColor

val ColorScheme.blueTray: Color
    @Composable
    get() = if (isSystemInDarkTheme()) DarkBlueTrayColor else LightBlueTrayColor

val ColorScheme.backSecond: Color
    @Composable
    get() = if (isSystemInDarkTheme()) BackSecondaryDarkColor else BackSecondaryLightColor






private val LightColorScheme = lightColorScheme(
    background = BackPrimaryLightColor,
    outline = SupportSeparatorLightColor,
    onPrimary = LabelPrimaryLightColor,
    onSecondary = LabelSecondaryLightColor,
    onTertiary = LabelTertiaryLightColor,
    surface = BackPrimaryLightColor,
)

@Composable
fun ToDoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

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