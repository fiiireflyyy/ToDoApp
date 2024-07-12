package com.fenix.todoapp.app

import android.app.Application
import com.fenix.todoapp.di.app.AppComponent
import com.fenix.todoapp.di.app.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}