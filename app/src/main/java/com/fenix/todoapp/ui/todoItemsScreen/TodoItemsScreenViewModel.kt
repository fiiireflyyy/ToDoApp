package com.fenix.todoapp.ui.todoItemsScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.fenix.todoapp.data.repository.TodoItemsRepository
import com.fenix.todoapp.domain.model.TodoItem
import com.fenix.todoapp.navigation.Screen
import com.fenix.todoapp.ui.todoItemsScreen.state.TodoItemModelUi
import com.fenix.todoapp.ui.todoItemsScreen.state.TodoItemsScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TodoItemsScreenViewModel @Inject constructor(
    private val navController: NavController,
    private val repository: TodoItemsRepository,
) : ViewModel(){

    private var todoItems = listOf<TodoItemModelUi>()

    private val _todoItemsScreenUiState = MutableStateFlow<TodoItemsScreenState>(TodoItemsScreenState.Loading)
    val todoItemsScreenUiState = _todoItemsScreenUiState.asStateFlow()

    init{
        loadTodoItems()
    }

    private fun loadTodoItems(){
        repository.todoItems.collectIn(viewModelScope){ todoItemsList ->

            val currentTodoItemsScreenUiState = todoItemsScreenUiState.value
            val isShowDone = when(currentTodoItemsScreenUiState){
                is TodoItemsScreenState.Success -> currentTodoItemsScreenUiState.isShowDone
                else -> false
            }
            todoItems = todoItemsList.map { it.toTodoItemsUiModel() }
            _todoItemsScreenUiState.value = TodoItemsScreenState.Success(
                todoItems = if (isShowDone) {
                    todoItems.filter { !it.isDone }
                } else{
                    todoItems
                },
                completedCount = todoItems.count { it.isDone },
                isShowDone = isShowDone,
            )
        }
    }

    fun changeShowDone(value: Boolean){
        _todoItemsScreenUiState.value = TodoItemsScreenState.Success(
            todoItems = if(value){
                todoItems.filter { !it.isDone }
            } else{
                todoItems
            },
            completedCount = todoItems.count { it.isDone },
            isShowDone = value,
        )
    }


    fun navigateToAddTodo(id: String?){
        navController.navigate("${Screen.AddTodoScreen.route}/${id}")
    }

    fun updateTodoItem(id: String, isDone: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTodoItem(id, isDone)
        }
    }

    fun deleteTodo(todoId: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTodo(todoId)
        }
    }

    fun TodoItem.toTodoItemsUiModel(): TodoItemModelUi{
        return TodoItemModelUi(
            id = id,
            description = description,
            isDone = isDone,
            importance = importance,
            deadline = deadline,
        )
    }


    fun <T> Flow<T>.collectIn(scope: CoroutineScope, action: (T) -> Unit){
        scope.launch(Dispatchers.IO) {
            collect(action)
        }
    }

}

