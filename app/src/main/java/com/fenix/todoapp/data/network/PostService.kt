package com.fenix.todoapp.data.network

import com.fenix.todoapp.data.network.dto.PatchPost
import com.fenix.todoapp.data.network.dto.Response
import com.fenix.todoapp.data.network.dto.TodoItemDto
import com.fenix.todoapp.data.network.dto.TodoItemPost
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import javax.inject.Inject

/**
 * [PostService] responsible for making network request
 */
class PostService @Inject constructor(
    private val client: HttpClient
) {

    suspend fun getList() : List<TodoItemDto> {
        val result = client.get { url(HttpRoutes.LIST) }
        val response : Response = result.body()
        return response.todoItemDtos
    }

    suspend fun getRevision() : Int {
        val result = client.get { url(HttpRoutes.LIST) }
        val response : Response = result.body()
        return response.revision
    }

    suspend fun addTodo(postItem: TodoItemPost, revision: String): String {
        val result = client.post(HttpRoutes.LIST){
            header("X-Last-Known-Revision", revision)
            contentType(ContentType.Application.Json)
            setBody(postItem)
        }
        val response : TodoItemPost = result.body()
        return response.status
    }

    suspend fun updateTodo(postItem: TodoItemPost, revision: String): String {
        val result = client.put(HttpRoutes.LIST) {
            url {
                appendPathSegments(postItem.todoItemDto.id)
            }
            header("X-Last-Known-Revision", revision)
            contentType(ContentType.Application.Json)
            setBody(postItem)
        }
        val response : TodoItemPost = result.body()
        return response.status
    }

    suspend fun deleteTodo(id: String, revision: String): String {
        val result = client.delete(HttpRoutes.LIST){
            url {
                appendPathSegments(id)
            }
            header("X-Last-Known-Revision", revision)
        }
        val response : TodoItemPost = result.body()
        return response.status
    }

    suspend fun patchTodo(patchPost: PatchPost, revision: Int): List<TodoItemDto> {
        val result = client.patch(HttpRoutes.LIST) {
            header("X-Last-Known-Revision", revision)
            contentType(ContentType.Application.Json)
            setBody(patchPost)
        }
        val response: Response = result.body()
        return response.todoItemDtos
    }

}


