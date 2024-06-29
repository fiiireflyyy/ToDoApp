package com.fenix.todoapp.data.repository

import android.util.Log
import com.fenix.todoapp.data.Result
import com.fenix.todoapp.domain.model.Importance
import com.fenix.todoapp.domain.model.TodoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
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

    val todoItems: StateFlow<List<TodoItem>> get() = _todoItems


    suspend fun addTodoItem(item: TodoItem){
        withContext(Dispatchers.IO){
            _todoItems.update {
                val list = it.toMutableList()
                list.also {
                    it.add(item)
                }
            }
        }
    }

    suspend fun updateTodoItem(id: String, isDone: Boolean){
        withContext(Dispatchers.IO){
            _todoItems.update {
                val list = it.toMutableList()
                list.forEachIndexed { index: Int, todoItem: TodoItem ->
                    if (todoItem.id == id) {
                        list[index] = todoItem.copy(
                            isDone = isDone,
                        )
                    }
                }
                list
            }
        }
    }

    suspend fun changeTodoItem(item: TodoItem?){
        withContext(Dispatchers.IO){
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
    }

    fun getItemById(todoId: String) : Flow<Result<TodoItem>> = flow{
        try{
            val item = _todoItems.value.find { it.id == todoId }
            item?.let {
                emit(Result.Success(item))
            }?: throw Exception("Exception of getting item")
        } catch (e: Exception){
            emit(Result.Error(e))
        }
    }.flowOn(Dispatchers.IO)


    suspend fun deleteTodo(id: String){
        withContext(Dispatchers.IO){
            _todoItems.update {
                it.filterNot { it.id == id }
            }
        }
    }
}