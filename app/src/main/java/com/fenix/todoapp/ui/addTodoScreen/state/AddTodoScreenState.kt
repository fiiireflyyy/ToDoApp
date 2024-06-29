package com.fenix.todoapp.ui.addTodoScreen.state

import com.fenix.todoapp.domain.model.TodoItem

sealed interface AddTodoScreenState {
    data object Loading: AddTodoScreenState
    data object Success: AddTodoScreenState
    data class Error(
        val message: String,
    ) : AddTodoScreenState
}