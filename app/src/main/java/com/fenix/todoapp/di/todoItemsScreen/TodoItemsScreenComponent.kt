package com.fenix.todoapp.di.todoItemsScreen

import androidx.navigation.NavController
import com.fenix.todoapp.data.repository.TodoItemsRepository
import com.fenix.todoapp.di.networkModule.NetworkModule
import com.fenix.todoapp.ui.todoItemsScreen.TodoItemsScreenViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@Singleton
interface TodoItemsScreenComponent {

    fun todoitemsViewModel(): TodoItemsScreenViewModel

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance navController: NavController, @BindsInstance repository: TodoItemsRepository) : TodoItemsScreenComponent
    }
}