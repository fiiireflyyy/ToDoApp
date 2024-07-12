package com.fenix.todoapp.di.app

import com.fenix.todoapp.ui.MainActivity
import com.fenix.todoapp.data.repository.TodoItemsRepository
import com.fenix.todoapp.di.activity.MainActivityComponent
import com.fenix.todoapp.di.activity.MainActivityModule
import com.fenix.todoapp.di.networkModule.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@AppScope
interface AppComponent {
    fun mainActivityComponent(activityModule: MainActivityModule): MainActivityComponent
}