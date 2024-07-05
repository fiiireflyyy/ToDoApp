package com.fenix.todoapp.ui.todoItemsScreen.state

sealed interface TodoItemsScreenUiEffects {
    data object SomethingWentWrongMessage : TodoItemsScreenUiEffects
    data class CustomMessage(val message: String) : TodoItemsScreenUiEffects
}