package com.fenix.todoapp.domain.mapper

import com.fenix.todoapp.data.dp.TodoItemEntity
import com.fenix.todoapp.data.network.dto.PatchPost
import com.fenix.todoapp.data.network.dto.TodoItemDto
import com.fenix.todoapp.data.network.dto.TodoItemPost
import com.fenix.todoapp.domain.model.Importance
import com.fenix.todoapp.domain.model.TodoItem
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject
/**
 * [TodoToPostMapper] responsible for transform data item
 */
class TodoToPostMapper @Inject constructor() {

    fun mapModelToPost(todoItem: TodoItem): TodoItemPost {
        return TodoItemPost(
            status = "ok",
            todoItemDto = mapModelToDto(todoItem)
        )
    }

    fun mapModelToDto(todoItem: TodoItem): TodoItemDto {
        return TodoItemDto(
            id = todoItem.id,
            text = todoItem.text,
            importance = todoItem.importance.toStringImportance(),
            color = "#FFFFFF",
            deadLine = todoItem.deadline,
            isCompleted = todoItem.isCompleted,
            dateOfCreation = todoItem.dateOfCreation,
            dateOfChange = todoItem.dateOfChange,
            user = "1",
        )
    }

    fun mapDtoToModel(todoItemDto: TodoItemDto): TodoItem {
        return TodoItem(
            id = todoItemDto.id,
            text = todoItemDto.text,
            importance = todoItemDto.importance.toImportance(),
            deadline = todoItemDto.deadLine,
            isCompleted = todoItemDto.isCompleted,
            dateOfCreation = todoItemDto.dateOfCreation,
            dateOfChange = todoItemDto.dateOfChange,
        )
    }

    fun mapEntityToModel(todoItemEntity: TodoItemEntity): TodoItem {
        return TodoItem(
            id = todoItemEntity.id,
            text = todoItemEntity.text,
            importance = todoItemEntity.importance.toImportance(),
            deadline = todoItemEntity.deadLine,
            isCompleted = todoItemEntity.isCompleted,
            dateOfCreation = todoItemEntity.creationDate,
            dateOfChange = todoItemEntity.refactorDate,
        )
    }

    fun mapModelToEntity(todoItem: TodoItem): TodoItemEntity{
        return TodoItemEntity(
            id = todoItem.id,
            text = todoItem.text,
            importance = todoItem.importance.toStringImportance(),
            deadLine = todoItem.deadline,
            isCompleted = todoItem.isCompleted,
            creationDate = todoItem.dateOfCreation,
            refactorDate = todoItem.dateOfChange,
            device = "1"
        )
    }

}

private fun String.toImportance(): Importance = when (this.lowercase()) {
    "low" -> Importance.Low
    "basic" -> Importance.Medium
    "important" -> Importance.High
    else -> throw IllegalArgumentException("Unknown importance level: $this")
}

private fun Importance.toStringImportance(): String = when (this) {
    is Importance.Low -> "low"
    is Importance.Medium -> "basic"
    is Importance.High -> "important"
}