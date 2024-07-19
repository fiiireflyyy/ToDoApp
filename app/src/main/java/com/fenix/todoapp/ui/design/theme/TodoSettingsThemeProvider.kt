package com.fenix.todoapp.ui.design.theme

object TodoSettingsThemeProvider {
    var current: ToDoThemeColors = ToDoThemeColors()
}

data class ToDoThemeColors(
    var darkTheme: Boolean = false
)