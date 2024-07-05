package com.fenix.todoapp.di.addTodoComponent

import androidx.navigation.NavController
import com.fenix.todoapp.data.repository.TodoItemsRepository
import com.fenix.todoapp.di.networkModule.NetworkModule
import com.fenix.todoapp.ui.addTodoScreen.AddTodoScreenViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@Singleton
interface AddTodoScreenComponent {

    fun addItemsViewModel(): AddTodoScreenViewModel

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance navController: NavController, @BindsInstance repository: TodoItemsRepository) : AddTodoScreenComponent
    }
}