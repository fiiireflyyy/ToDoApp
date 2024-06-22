package com.fenix.todoapp.di.todoItemsScreen

import androidx.navigation.NavController
import com.fenix.todoapp.data.repository.TodoItemsRepository
import com.fenix.todoapp.ui.todoItemsScreen.TodoItemsScreenViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component
@Singleton
interface TodoItemsScreenComponent {

    fun todoitemsViewModel(): TodoItemsScreenViewModel

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance navController: NavController, @BindsInstance repository: TodoItemsRepository) : TodoItemsScreenComponent
    }
}