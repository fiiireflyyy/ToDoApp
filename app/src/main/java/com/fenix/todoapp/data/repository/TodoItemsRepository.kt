package com.fenix.todoapp.data.repository

import android.util.Log
import com.fenix.todoapp.data.network.NetworkConnection
import com.fenix.todoapp.di.activity.MainActivityScope
import com.fenix.todoapp.domain.model.TodoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * [TodoItemsRepository] responsible for managing data
// */
@MainActivityScope
class TodoItemsRepository @Inject constructor(
    private val networkTodoItemsRepository: NetworkTodoItemsRepository,
    private val localTodoItemsRepository: LocalTodoItemsRepository,
    private val networkConnection: NetworkConnection,
) {

    private val _todoItems = MutableStateFlow<List<TodoItem>>(emptyList())
    val todoItems = _todoItems.asStateFlow()

    private var isNetworkAvailable: Boolean = false


    init {
        collectNetworkState()
    }


    suspend fun getTodoItems() {
        withContext(Dispatchers.IO) {
            val todoItemsLocal = localTodoItemsRepository.getTodoItems()
            val todoItemsNetwork = networkTodoItemsRepository.getTodoItems()
            if (isNetworkAvailable) {
                val result = if (todoItemsLocal.isNotEmpty()) {
                    networkTodoItemsRepository.patchTodoItems(todoItemsLocal)
                } else {
                    networkTodoItemsRepository.getTodoItems()
                }
                if (result.isSuccess) {
                    val items = result.getOrNull()
                    if (items != null) {
                        localTodoItemsRepository.upsertList(items)
                        _todoItems.value = items
                    }
                } else {
                    _todoItems.value = todoItemsLocal
                }
            } else {
                _todoItems.value = todoItemsLocal
            }
        }
    }

    suspend fun updateTodoItem(todoItem: TodoItem): Result<Unit> {
        withContext(Dispatchers.IO) {
            localTodoItemsRepository.upsertTodoItem(todoItem)
            if (isNetworkAvailable) {
                networkTodoItemsRepository.updateTodoItem(todoItem)
            }
            _todoItems.value = todoItems.value.map {
                if (it.id == todoItem.id) {
                    todoItem
                } else {
                    it
                }
            }
        }
        return Result.success(Unit)
    }

    suspend fun addTodoItem(todoItem: TodoItem): Result<Unit> {
        withContext(Dispatchers.IO) {
            localTodoItemsRepository.upsertTodoItem(todoItem)
            if (isNetworkAvailable) {
                networkTodoItemsRepository.addTodoItem(todoItem)
            }
            _todoItems.value = todoItems.value + todoItem
        }
        return Result.success(Unit)
    }

    suspend fun deleteTodoItem(id: String): Result<Unit> {
        withContext(Dispatchers.IO) {
            localTodoItemsRepository.deleteItem(id)
            if (isNetworkAvailable) {
                networkTodoItemsRepository.deleteItem(id)
            }
            _todoItems.value = todoItems.value.filterNot { it.id == id }
        }
        return Result.success(Unit)
    }

    suspend fun updateCompleted(
        id: String,
        isCompleted: Boolean,
        dateOfChange: Long
    ): Result<Unit> {
        withContext(Dispatchers.IO) {

            val newItem = todoItems.value
                .firstOrNull { it.id == id }
                ?.copy(
                    isCompleted = isCompleted,
                    dateOfChange = dateOfChange,
                )
                ?: return@withContext
            localTodoItemsRepository.upsertTodoItem(newItem)
            if (isNetworkAvailable) {
                networkTodoItemsRepository.updateTodoItem(newItem)
            }
            _todoItems.value = todoItems.value.map {
                if (it.id == id) {
                    it.copy(
                        isCompleted = isCompleted,
                        dateOfChange = dateOfChange,
                    )
                } else {
                    it
                }
            }

        }
        return Result.success(Unit)
    }

    suspend fun getItem(id: String): Result<TodoItem> {
        return withContext(Dispatchers.IO) {
            Result.success(localTodoItemsRepository.getItem(id))
        }
    }

    private fun collectNetworkState() {
        CoroutineScope(Dispatchers.IO).launch {
            networkConnection.observeNetworkState().collect { networkState ->
                isNetworkAvailable = networkState
                if (isNetworkAvailable) {
                    getTodoItems()
                }
            }
        }
    }

}
