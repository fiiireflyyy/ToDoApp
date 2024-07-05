package com.fenix.todoapp.di.app

import com.fenix.todoapp.MainActivity
import com.fenix.todoapp.app.App
import com.fenix.todoapp.data.repository.TodoItemsRepository
import com.fenix.todoapp.di.networkModule.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@Singleton
interface AppComponent {
    fun inject(activity: MainActivity)

    fun todoItemsRepository(): TodoItemsRepository

    @Component.Factory
    interface Factory{
        fun create() : AppComponent
    }
}