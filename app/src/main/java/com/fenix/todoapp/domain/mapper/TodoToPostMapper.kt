package com.fenix.todoapp.domain.mapper

import com.fenix.todoapp.data.network.dto.PostTodo
import com.fenix.todoapp.data.network.dto.TodoItemDto
import com.fenix.todoapp.domain.model.Importance
import com.fenix.todoapp.domain.model.TodoItem
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject

class TodoToPostMapper @Inject constructor() {

    fun mapToPost(todoItem: TodoItem) : PostTodo {
        return PostTodo(
            status = "ok",
            element = mapToDto(todoItem)
        )
    }
    fun mapToDto(todoItem: TodoItem) : TodoItemDto {
        return TodoItemDto(
            id = todoItem.id,
            text = todoItem.description,
            importance = todoItem.importance.level,
            color = "#FFFFFF",
            deadLine = todoItem.deadline?.toUnixTimestamp(),
            isCompleted = todoItem.isDone,
            creationDate =  todoItem.creationDate.toUnixTimestamp(),
            refactorDate = 1720082697,
            byPhone = "1",
        )
    }

    fun mapDtoToLocal(todoItemDto: TodoItemDto) : TodoItem {
        return TodoItem(
            id = todoItemDto.id,
            description = todoItemDto.text,
            importance = todoItemDto.importance.toImportance(),
            deadline = todoItemDto.deadLine?.toDate(),
            isDone = todoItemDto.isCompleted,
            creationDate = todoItemDto.creationDate.toLocalDateTime(),
        )
    }
}
fun LocalDateTime.toUnixTimestamp(): Long {
    return this.atZone(ZoneId.systemDefault()).toEpochSecond()
}
fun Long.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.ofInstant(Instant.ofEpochSecond(this), ZoneId.systemDefault())
}
fun Date.toUnixTimestamp(): Long {
    return this.time / 1000
}
fun Long.toDate(): Date {
    return Date(this * 1000)
}
fun Importance.toStringValue(): String {
    return this.level
}
fun String.toImportance(): Importance = when (this.lowercase()) {
    "low" -> Importance.Low
    "basic" -> Importance.Medium
    "important" -> Importance.High
    else -> throw IllegalArgumentException("Unknown importance level: $this")
}