package com.fenix.todoapp.domain.model

sealed class SettingsTheme(val settingsTheme: String) {
    data object DarkThemeChoice : SettingsTheme("darkThemeChoice")
    data object LightThemeChoice : SettingsTheme("lightThemeChoice")
    data object SystemThemeChoice : SettingsTheme("systemThemeChoice")
}