package com.fenix.todoapp.app

import android.app.Application
import com.fenix.todoapp.data.repository.TodoItemsRepository
import com.fenix.todoapp.di.app.AppComponent
import com.fenix.todoapp.di.app.DaggerAppComponent

class App: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.create()
    }
}