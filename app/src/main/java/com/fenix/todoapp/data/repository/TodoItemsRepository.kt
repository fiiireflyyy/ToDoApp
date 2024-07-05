package com.fenix.todoapp.data.repository

import android.util.Log
import com.fenix.todoapp.data.Result
import com.fenix.todoapp.data.network.PostService
import com.fenix.todoapp.data.network.dto.PostTodo
import com.fenix.todoapp.data.network.dto.TodoItemDto
import com.fenix.todoapp.domain.mapper.TodoToPostMapper
import com.fenix.todoapp.domain.model.Importance
import com.fenix.todoapp.domain.model.TodoItem
import io.ktor.client.plugins.ResponseException
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
class TodoItemsRepository @Inject constructor(
    private val postService: PostService,
    private val mapper: TodoToPostMapper,
) {

    private val _todoItems = MutableStateFlow<Result<List<TodoItem>>?>(null)

    val todoItems: StateFlow<Result<List<TodoItem>>?> get() = _todoItems

    suspend fun getList(){
        withContext(Dispatchers.IO) {
            try {
                val list = postService.getList()
                val data = list.map { mapper.mapDtoToLocal(it) }
                _todoItems.value = Result.Success(
                    data,
                )
            } catch (e: ResponseException) {
                _todoItems.value = Result.Error(Exception(e.response.status.description))
            } catch (e: Exception) {
                _todoItems.value = Result.Error(Exception(e.message))
            }
        }
    }

    suspend fun addTodoItem(item: TodoItem){
        withContext(Dispatchers.IO){
            try {
                val revision = postService.getRevision()
                postService.addTodo(mapper.mapToPost(item), revision.toString())
                _todoItems.update {
                    val list = (it as Result.Success).data + item
                    Result.Success(list)
                }
            } catch (e: ResponseException) {
                _todoItems.value = Result.Error(Exception(e.response.status.description))
            } catch (e: Exception) {
                Log.d("TESTLOG","ПОПАЛ В ЕРОР")
                _todoItems.value = Result.Error(Exception(e.message))
            }
        }
    }

    suspend fun updateTodoItem(id: String, isDone: Boolean){
        withContext(Dispatchers.IO){
            try {
                var updateItem : TodoItem? = null
                if(_todoItems.value is Result.Success){
                    updateItem = (_todoItems.value as Result.Success)
                        .data
                        .find { it.id == id }
                        ?.copy(
                            isDone = isDone,
                        )
                    _todoItems.update {
                        val list = (it as Result.Success).data.toMutableList()
                        list.forEachIndexed { index: Int, todoItem: TodoItem ->
                            if (todoItem.id == id) {
                                list[index] = updateItem!!
                            }
                        }
                        Result.Success(list)
                    }
                }
                val revision = postService.getRevision()
                val status = postService.updateTodo(mapper.mapToPost(updateItem!!), revision.toString())
                if(status != "ok"){
                    val list = postService.getList()
                    val data = list.map { mapper.mapDtoToLocal(it) }
                    _todoItems.value = Result.Success(
                        data,
                    )
                }
            } catch (e: ResponseException) {
                _todoItems.value = Result.Error(Exception(e.response.status.description))
            } catch (e: Exception) {
                _todoItems.value = Result.Error(Exception(e.message))
            }
        }
    }

    suspend fun changeTodoItem(item: TodoItem?){
        withContext(Dispatchers.IO) {
            try {
                val revision = postService.getRevision()
                postService.updateTodo(mapper.mapToPost(item!!), revision.toString())
                val list = postService.getList()
                val data = list.map { mapper.mapDtoToLocal(it) }
                _todoItems.value = Result.Success(
                    data,
                )
            } catch (e: ResponseException) {
                _todoItems.value = Result.Error(Exception(e.response.status.description))
            } catch (e: Exception) {
                _todoItems.value = Result.Error(Exception(e.message))
            }
        }
    }

    fun getItemById(todoId: String) : Result<TodoItem> {
        try{
            val item = (_todoItems.value as Result.Success).data.find { it.id == todoId }
            item?.let {
                return Result.Success(item)
            }?: throw Exception("Exception of getting item")
        } catch (e: Exception){
            return Result.Error(e)
        }
    }


    suspend fun deleteTodo(id: String){
        withContext(Dispatchers.IO){
            try {
                val revision = postService.getRevision()
                postService.deleteTodo(id, revision.toString())
                val list = postService.getList()
                val data = list.map { mapper.mapDtoToLocal(it) }
                _todoItems.value = Result.Success(
                    data,
                )
            } catch (e: ResponseException) {
                _todoItems.value = Result.Error(Exception("DELETE  ${e.response.status}"))
            } catch (e: Exception) {
                _todoItems.value = Result.Error(Exception(e.message))
            }
        }
    }
}