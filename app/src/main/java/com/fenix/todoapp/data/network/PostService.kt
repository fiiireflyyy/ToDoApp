package com.fenix.todoapp.data.network

import com.fenix.todoapp.data.network.dto.PostTodo
import com.fenix.todoapp.data.network.dto.Response
import com.fenix.todoapp.data.network.dto.TodoItemDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
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
        return response.list
    }

    suspend fun getRevision() : Int {
        val result = client.get { url(HttpRoutes.LIST) }
        val response : Response = result.body()
        return response.revision
    }

    suspend fun addTodo(postItem: PostTodo, revision: String): String {
        val result = client.post(HttpRoutes.LIST){
            header("X-Last-Known-Revision", revision)
            contentType(ContentType.Application.Json)
            setBody(postItem)
        }
        val response : PostTodo = result.body()
        return response.status
    }

    suspend fun updateTodo(postItem: PostTodo, revision: String): String {
        val result = client.put(HttpRoutes.LIST) {
            url {
                appendPathSegments(postItem.element.id)
            }
            header("X-Last-Known-Revision", revision)
            contentType(ContentType.Application.Json)
            setBody(postItem)
        }
        val response : PostTodo = result.body()
        return response.status
    }

    suspend fun deleteTodo(id: String, revision: String): String {
        val result = client.delete(HttpRoutes.LIST){
            url {
                appendPathSegments(id)
            }
            header("X-Last-Known-Revision", revision)
        }
        val response : PostTodo = result.body()
        return response.status
    }
}


