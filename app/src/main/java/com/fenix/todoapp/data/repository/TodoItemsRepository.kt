package com.fenix.todoapp.data.repository

import com.fenix.todoapp.data.Result
import com.fenix.todoapp.data.network.PostService
import com.fenix.todoapp.di.activity.MainActivityScope
import com.fenix.todoapp.domain.mapper.TodoToPostMapper
import com.fenix.todoapp.domain.model.TodoItem
import io.ktor.client.plugins.ResponseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
/**
 * [TodoItemsRepository] responsible for managing data
 */
@MainActivityScope
class TodoItemsRepository @Inject constructor(
    private val postService: PostService,
    private val mapper: TodoToPostMapper,
) {

    private var itemsList = emptyList<TodoItem>()

    private val _todoItems = MutableStateFlow<Result<List<TodoItem>>?>(null)
    val todoItems: StateFlow<Result<List<TodoItem>>?> get() = _todoItems

    suspend fun getList() {
        withContext(Dispatchers.IO) {
            try {
                val list = postService.getList()
                itemsList = list.map { mapper.mapDtoToLocal(it) }
                _todoItems.value = Result.Success(
                    itemsList,
                )
            } catch (e: ResponseException) {
                _todoItems.value = Result.Error(Exception(e.response.status.description))
            } catch (e: Exception) {
                _todoItems.value = Result.Error(Exception("Проверьте подключение к интернету"))
            }
        }
    }

    suspend fun addTodoItem(item: TodoItem): Result<Unit> {
        return withContext(Dispatchers.IO){
            try {
                val revision = postService.getRevision()
                postService.addTodo(mapper.mapToPost(item), revision.toString())
                itemsList += item
                _todoItems.value = Result.Success(itemsList)
                Result.Success(Unit)
            } catch (e: ResponseException) {
                Result.Error(Exception(e.response.status.description))
            } catch (e: Exception) {
                Result.Error(Exception("Ошибка добавления (проверьте подключение)"))
            }
        }
    }

    suspend fun updateTodoItem(id: String, isDone: Boolean): Result<Unit> {

        val itemToUpdate = itemsList.firstOrNull { it.id == id }
            ?.copy(isDone = isDone)
            ?: return Result.Error(Exception("List doesn't contains item"))

        return try {
            val revision = postService.getRevision()
            postService.updateTodo(mapper.mapToPost(itemToUpdate), revision.toString())

            itemsList = itemsList.map {
                if (it.id == id) it.copy(isDone = isDone) else it
            }
            _todoItems.value = Result.Success(itemsList)
            Result.Success(Unit)
        } catch (e: ResponseException) {
            Result.Error(Exception(("CHANGE  ${e.response.status.description}")))
        } catch (e: Exception) {
            Result.Error(Exception("Ошибка изменения"))
        }
    }

    suspend fun changeTodoItem(item: TodoItem?) :  Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val revision = postService.getRevision()
                postService.updateTodo(mapper.mapToPost(item!!), revision.toString())
                val list = postService.getList()
                itemsList = list.map { mapper.mapDtoToLocal(it) }
                _todoItems.value = Result.Success(
                    itemsList,
                )
                Result.Success(Unit)
            } catch (e: ResponseException) {
                Result.Error(Exception("CHANGE  ${e.response.status.description}"))
            } catch (e: Exception) {
                Result.Error(Exception("Ошибка изменения (проверьте подключение)"))
            }
        }
    }

    fun getItemById(todoId: String) : Result<TodoItem> {
        return try {
            val item = itemsList.find { it.id == todoId }
            if (item == null) {
                Result.Error(Exception("Items list doesn't contain item"))
            } else {
                Result.Success(item)
            }
        } catch (e: Exception){
            Result.Error(e)
        }
    }

    suspend fun deleteTodo(id: String) {
        withContext(Dispatchers.IO){
            try {
                val revision = postService.getRevision()
                postService.deleteTodo(id, revision.toString())
                itemsList = itemsList.filter { it.id != id }
                _todoItems.value = Result.Success(
                    itemsList,
                )
            } catch (e: ResponseException) {
                _todoItems.value = Result.Error(Exception("DELETE  ${e.response.status.description}"))
            } catch (e: Exception) {
                _todoItems.value = Result.Error(Exception("Ошибка удаления (проверьте подключение)"))
            }
        }
    }
}