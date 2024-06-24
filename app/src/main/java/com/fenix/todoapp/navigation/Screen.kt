package com.fenix.todoapp.navigation

sealed class Screen(val route: String) {
    data object TodoItemsScreen : Screen("todo_items_screen")
    data object AddTodoScreen : Screen("add_todo_screen")
}