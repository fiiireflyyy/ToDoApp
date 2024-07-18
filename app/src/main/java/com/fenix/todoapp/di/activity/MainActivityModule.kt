package com.fenix.todoapp.di.activity

import com.fenix.todoapp.ui.MainActivity
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule(private val activity: MainActivity) {

    @Provides
    fun provideMainActivity(): MainActivity {
        return activity
    }
}