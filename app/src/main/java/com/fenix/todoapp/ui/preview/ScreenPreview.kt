package com.fenix.todoapp.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.fenix.todoapp.domain.model.Importance
import com.fenix.todoapp.ui.design.theme.ToDoTheme
import com.fenix.todoapp.ui.todoItemsScreen.composables.ListTodoItems
import com.fenix.todoapp.ui.todoItemsScreen.state.TodoItemModelUi

@Preview
@Composable
private fun LightScreenPreview(){
    ToDoTheme {
        ListTodoItems(
            todoItems = listOf(TodoItemModelUi(
                id = "1",
                description = "Превью экрана",
                isDone = true,
                importance = Importance.High),
            ),
            completedCount = 1,
            isShowDone = true,
            onCheckedChange = { _, _ -> },
            changeShowDone = {  },
            onDelete = {  }
        ) {

        }
    }
}

@Preview()
@Composable
private fun DarkScreenPreview(){
    ToDoTheme(
        darkTheme = true
    ) {
        ListTodoItems(
            todoItems = listOf(TodoItemModelUi(
                id = "1",
                description = "Превью экрана",
                isDone = true,
                importance = Importance.High),
            ),
            completedCount = 1,
            isShowDone = true,
            onCheckedChange = { _, _ -> },
            changeShowDone = {  },
            onDelete = {  }
        ) {

        }
    }
}