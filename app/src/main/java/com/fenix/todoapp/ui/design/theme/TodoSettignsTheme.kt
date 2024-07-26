package com.fenix.todoapp.ui.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.fenix.todoapp.domain.model.SettingsTheme

@Composable
fun ToDoThemeWithUserChoice(
    userThemeChoice: SettingsTheme,
    content: @Composable () -> Unit,
) {
    ToDoTheme(
        darkTheme = when (userThemeChoice) {
            is SettingsTheme.LightThemeChoice -> false
            is SettingsTheme.DarkThemeChoice -> true
            is SettingsTheme.SystemThemeChoice -> isSystemInDarkTheme()
        },
        content = content,
    )
}