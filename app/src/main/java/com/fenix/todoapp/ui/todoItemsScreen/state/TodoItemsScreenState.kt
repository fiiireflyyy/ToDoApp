package com.fenix.todoapp.ui.todoItemsScreen.state

sealed interface TodoItemsScreenState {
    data object Loading: TodoItemsScreenState
    data class Success(
        val todoItems: List<TodoItemModelUi>,
        val completedCount: Int,
        val isShowDone: Boolean,
    ) : TodoItemsScreenState
    data class Error(
        val message: String,
    ) : TodoItemsScreenState
}