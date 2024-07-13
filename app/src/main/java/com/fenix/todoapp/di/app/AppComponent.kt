package com.fenix.todoapp.di.app

import android.content.Context
import androidx.room.Room
import com.fenix.todoapp.app.App
import com.fenix.todoapp.data.dp.TodoDao
import com.fenix.todoapp.data.dp.TodoDatabase
import com.fenix.todoapp.di.activity.MainActivityComponent
import com.fenix.todoapp.di.activity.MainActivityModule
import com.fenix.todoapp.di.networkModule.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.Provides

@Component(modules = [NetworkModule::class])
@AppScope
interface AppComponent {
    fun mainActivityComponent(activityModule: MainActivityModule): MainActivityComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context) : AppComponent
    }

}