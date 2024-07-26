package com.fenix.todoapp.di.settingsThemeScreen

import com.fenix.todoapp.ui.settingsThemeScreen.SettingsThemeFragment
import dagger.Subcomponent


@SettingsThemeScope
@Subcomponent
interface SettingsThemeScreenComponent {
    fun inject(fragment: SettingsThemeFragment)
}