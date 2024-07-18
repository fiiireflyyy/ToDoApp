package com.fenix.todoapp.data.repository

import com.fenix.todoapp.data.dp.TodoDao
import com.fenix.todoapp.di.activity.MainActivityScope
import com.fenix.todoapp.domain.mapper.TodoToPostMapper
import com.fenix.todoapp.domain.model.TodoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@MainActivityScope
class LocalTodoItemsRepository @Inject constructor(
    private val dao: TodoDao,
    private val mapper: TodoToPostMapper,
) {

    suspend fun getTodoItems(): List<TodoItem>{
        return withContext(Dispatchers.IO){
            dao.getList().map {item -> mapper.mapEntityToModel(item) }
        }
    }

    suspend fun upsertTodoItem(todoItem: TodoItem){
        withContext(Dispatchers.IO){
            dao.upsertItem(
                mapper.mapModelToEntity(todoItem)
            )
            getTodoItems()
        }
    }

    suspend fun upsertList(todoItems: List<TodoItem>){
        withContext(Dispatchers.IO){
            dao.upsertItem(
                todoItems.map { mapper.mapModelToEntity(it) }
            )
            getTodoItems()
        }
    }

    suspend fun getItem(id: String): TodoItem{
        return withContext(Dispatchers.IO){
            mapper.mapEntityToModel(dao.getItem(id))
        }
    }

    suspend fun deleteItem(id: String){
        withContext(Dispatchers.IO){
            dao.deleteItem(id)
            getTodoItems()
        }
    }
}