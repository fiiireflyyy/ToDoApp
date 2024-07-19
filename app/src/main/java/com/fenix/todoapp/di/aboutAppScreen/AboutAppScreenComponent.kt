package com.fenix.todoapp.di.aboutAppScreen

import com.fenix.todoapp.ui.aboutAppScreen.AboutAppFragment
import dagger.Subcomponent

@AboutAppScope
@Subcomponent
interface AboutAppScreenComponent {
    fun inject(fragment: AboutAppFragment)
}