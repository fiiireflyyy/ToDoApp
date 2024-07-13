package com.fenix.todoapp.data.repository

import com.fenix.todoapp.data.network.PostService
import com.fenix.todoapp.data.network.dto.PatchPost
import com.fenix.todoapp.di.activity.MainActivityScope
import com.fenix.todoapp.domain.mapper.TodoToPostMapper
import com.fenix.todoapp.domain.model.TodoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@MainActivityScope
class NetworkTodoItemsRepository @Inject constructor(
    private val postService: PostService,
    private val mapper: TodoToPostMapper,
) {

    suspend fun getTodoItems(): Result<List<TodoItem>> {
        return withContext(Dispatchers.IO) {
            val list = postService.getList()
            Result.success(list.map { mapper.mapDtoToModel(it) })
        }
    }

    suspend fun addTodoItem(item: TodoItem): Result<Unit> {
        return withContext(Dispatchers.IO) {
            val revision = postService.getRevision()
            postService.addTodo(mapper.mapModelToPost(item), revision.toString())
            Result.success(Unit)

        }
    }

    suspend fun updateTodoItem(item: TodoItem): Result<Unit> {
        return withContext(Dispatchers.IO) {
            val revision = postService.getRevision()
            postService.updateTodo(mapper.mapModelToPost(item), revision.toString())
            Result.success(Unit)
        }
    }


    suspend fun deleteItem(id: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            val revision = postService.getRevision()
            postService.deleteTodo(id, revision.toString())
            Result.success(Unit)
        }
    }

    suspend fun patchTodoItems(list: List<TodoItem>): Result<List<TodoItem>> {
        return withContext(Dispatchers.IO) {
            val revision = postService.getRevision()
            val dtos = postService.patchTodo(
                 patchPost = PatchPost(
                    todoItemDtos = list.map { mapper.mapModelToDto(it) },
                    status = "ok"
                ),
                revision
            )
            Result.success(
                dtos.map { mapper.mapDtoToModel(it) }
            )
        }
    }
}