package com.fenix.todoapp.di.activity

import com.fenix.todoapp.di.aboutAppScreen.AboutAppScreenComponent
import com.fenix.todoapp.di.addTodoScreen.AddTodoScreenComponent
import com.fenix.todoapp.di.todoItemsScreen.TodoItemsScreenComponent
import com.fenix.todoapp.ui.MainActivity
import dagger.Subcomponent

@Subcomponent(modules = [MainActivityModule::class])
@MainActivityScope
interface MainActivityComponent {
    fun mainActivity(): MainActivity
    fun inject(activity: MainActivity)
    fun todoItemsFragmentComponent(): TodoItemsScreenComponent
    fun todoItemsAddFragmentComponent(): AddTodoScreenComponent
    fun aboutAppFragmentComponent(): AboutAppScreenComponent
}