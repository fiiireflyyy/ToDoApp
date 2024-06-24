package com.fenix.todoapp.di.addTodoComponent

import androidx.navigation.NavController
import com.fenix.todoapp.data.repository.TodoItemsRepository
import com.fenix.todoapp.ui.addTodoScreen.AddTodoScreenViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component
@Singleton
interface AddTodoScreenComponent {

    fun addItemsViewModel(): AddTodoScreenViewModel

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance navController: NavController, @BindsInstance repository: TodoItemsRepository) : AddTodoScreenComponent
    }
}