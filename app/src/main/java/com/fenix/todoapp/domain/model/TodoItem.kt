package com.fenix.todoapp.domain.model

import java.time.LocalDateTime
import java.util.Date

data class TodoItem(
    val id: String,
    val text: String,
    val deadline: Long?,
    val importance: Importance,
    val isCompleted: Boolean,
    val dateOfCreation: Long,
    val dateOfChange: Long?,
) {
    companion object {
        val defaultTodoItem = TodoItem(
            id = "",
            text = "",
            deadline = null,
            importance = Importance.Medium,
            isCompleted = false,
            dateOfCreation = 0L,
            dateOfChange = null,
        )
    }
}