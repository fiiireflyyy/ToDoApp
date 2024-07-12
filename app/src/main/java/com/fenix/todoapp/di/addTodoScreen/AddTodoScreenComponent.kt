package com.fenix.todoapp.di.addTodoScreen

import com.fenix.todoapp.ui.addTodoScreen.AddTodoFragment
import dagger.Subcomponent

@AddTodoScope
@Subcomponent
interface AddTodoScreenComponent {
    fun inject(fragment: AddTodoFragment)
}