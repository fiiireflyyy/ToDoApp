package com.fenix.todoapp.domain.model

import java.time.LocalDateTime
import java.util.Date

data class TodoItem(
    val id: String,
    val description: String,
    val importance: Importance,
    val isDone: Boolean,
    val creationDate: LocalDateTime,
    val deadline: Date? = null,
    val changeDate: LocalDateTime? = null,
)
