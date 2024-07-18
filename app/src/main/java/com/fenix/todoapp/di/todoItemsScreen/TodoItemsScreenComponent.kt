package com.fenix.todoapp.di.todoItemsScreen

import com.fenix.todoapp.ui.todoItemsScreen.TodoItemsFragment
import dagger.Subcomponent

@TodoItemsScope
@Subcomponent
interface TodoItemsScreenComponent {
    fun inject(fragment: TodoItemsFragment)
}