package com.fenix.todoapp.ui.todoItemsScreen.state

import com.fenix.todoapp.domain.model.Importance
import java.util.Date

data class TodoItemModelUi(
    val id: String,
    val description: String,
    val isDone: Boolean,
    val importance: Importance,
    val deadline: Date? = null,
)
