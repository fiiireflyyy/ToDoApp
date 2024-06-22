package com.fenix.todoapp.data.repository

import android.util.Log
import com.fenix.todoapp.domain.model.Importance
import com.fenix.todoapp.domain.model.TodoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoItemsRepository @Inject constructor() {

    private val _todoItems = MutableStateFlow(listOf(
        TodoItem(
            id = "1",
            description = "Купить что-то",
            importance = Importance.High,
            isDone = false,
            creationDate = LocalDateTime.now(),
        ),
        TodoItem(
            id = "2",
            description = "Купить что-то",
            importance = Importance.Medium,
            isDone = true,
            creationDate = LocalDateTime.now(),
        ),
        TodoItem(
            id = "3",
            description = "Купить что-то",
            importance = Importance.Medium,
            isDone = false,
            creationDate = LocalDateTime.now(),
        ),
        TodoItem(
            id = "4",
            description = "Купить что что что что что-тоооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооо",
            importance = Importance.Low,
            isDone = true,
            creationDate = LocalDateTime.now(),
        ),
        TodoItem(
            id = "5",
            description = "Купить что-то",
            importance = Importance.High,
            isDone = false,
            creationDate = LocalDateTime.now(),
        ),
        TodoItem(
            id = "6",
            description = "Купить что-то",
            importance = Importance.Medium,
            isDone = false,
            creationDate = LocalDateTime.now(),
        ),
        TodoItem(
            id = "7",
            description = "Купить что-то",
            importance = Importance.Medium,
            isDone = false,
            creationDate = LocalDateTime.now(),
        ),
        TodoItem(
            id = "8",
            description = "Купить что-то",
            importance = Importance.Medium,
            isDone = false,
            creationDate = LocalDateTime.now(),
        ),
        TodoItem(
            id = "9",
            description = "Купить что-то",
            importance = Importance.Medium,
            isDone = false,
            creationDate = LocalDateTime.now(),
        ),
        TodoItem(
            id = "10",
            description = "Купить что-то",
            importance = Importance.Medium,
            isDone = false,
            creationDate = LocalDateTime.now(),
        )
    ))

    val todoItems = _todoItems.asStateFlow()


    fun addTodoItem(item: TodoItem){
        _todoItems.update {
            val list = it.toMutableList()
            list.also {
                it.add(item)
            }
        }
        Log.d("mytag",this.toString())
        Log.d("mytag",todoItems.toString())
    }

    fun updateTodoItem(item: TodoItem?){
        _todoItems.update {
            val list = it.toMutableList()
            list.also {
                it.forEachIndexed{index, todoItem ->
                    if (todoItem.id == item!!.id){
                        list[index] = item
                    }
                }
            }
        }
    }


    fun getTodoItemById(todoId: String): TodoItem? {
        return todoItems.value.find { it.id == todoId }
    }


    fun deleteTodo(id: String){
        _todoItems.update {
            it.filterNot { it.id == id }
        }
    }
}