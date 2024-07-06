package com.fenix.todoapp.ui.todoItemsScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.fenix.todoapp.data.Result
import com.fenix.todoapp.data.repository.TodoItemsRepository
import com.fenix.todoapp.domain.model.TodoItem
import com.fenix.todoapp.navigation.Screen
import com.fenix.todoapp.ui.addTodoScreen.AddTodoScreenViewModel
import com.fenix.todoapp.ui.todoItemsScreen.state.TodoItemModelUi
import com.fenix.todoapp.ui.todoItemsScreen.state.TodoItemsScreenState
import com.fenix.todoapp.ui.todoItemsScreen.state.TodoItemsScreenUiEffects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton
/**
 * [TodoItemsScreenViewModel] is responsible for managing the UI-related data for the Todo screen.
 */

@Singleton
class TodoItemsScreenViewModel @Inject constructor(
    private val navController: NavController,
    private val repository: TodoItemsRepository,
) : ViewModel(){

    private var todoItems = listOf<TodoItemModelUi>()

    private val _todoItemsScreenUiState = MutableStateFlow<TodoItemsScreenState>(TodoItemsScreenState.Loading)
    val todoItemsScreenUiState = _todoItemsScreenUiState.asStateFlow()

    private val _uiEffectFlow = MutableSharedFlow<TodoItemsScreenUiEffects>()
    val uiEffectFlow = _uiEffectFlow.asSharedFlow()

    init {
        getListFromBase()
        loadTodoItems()
    }

    private fun loadTodoItems() {
        viewModelScope.launch {
            repository.todoItems.collect { todoItemsList ->
                Log.d("TESTLOG","ВЫЗВАЛСЯ КОЛЛЕКТ")
                val currentTodoItemsScreenUiState = todoItemsScreenUiState.value
                val isShowDone = when(currentTodoItemsScreenUiState){
                    is TodoItemsScreenState.Success -> currentTodoItemsScreenUiState.isShowDone
                    else -> false
                }
                when (todoItemsList) {
                    is Result.Success -> {
                        todoItems = todoItemsList.data.map { it.toTodoItemsUiModel() }
                        val itemsToShow = if (isShowDone) todoItems.filter { !it.isDone } else todoItems
                        _todoItemsScreenUiState.value = TodoItemsScreenState.Success(
                            todoItems = itemsToShow,
                            completedCount = todoItems.count { it.isDone },
                            isShowDone = isShowDone,
                        )
                    }
                    is Result.Error -> {
                        emitErrorMessage(todoItemsList.e.message)
                        _todoItemsScreenUiState.value = TodoItemsScreenState.Error
                    }
                    null -> {
                        _todoItemsScreenUiState.value = TodoItemsScreenState.Loading
                    }
                }
            }
        }
    }

    fun getListFromBase() {
        viewModelScope.launch {
            _todoItemsScreenUiState.value = TodoItemsScreenState.Loading
            repository.getList()
        }
    }

    fun changeShowDone(value: Boolean) {
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

    fun navigateToAddTodo(id: String?) {
        navController.navigate("${Screen.AddTodoScreen.route}/${id}")
    }

    fun updateTodoItem(id: String, isDone: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.updateTodoItem(id, isDone)) {
                is Result.Success -> {}
                is Result.Error -> emitErrorMessage(result.e.message)
            }
        }
    }

    fun deleteTodo(todoId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTodo(todoId)
        }
    }

    private suspend fun emitErrorMessage(message: String?) {
        if (message == null) {
            _uiEffectFlow.emit(TodoItemsScreenUiEffects.SomethingWentWrongMessage)
        } else {
            _uiEffectFlow.emit(TodoItemsScreenUiEffects.CustomMessage(message))
        }
    }

    private fun TodoItem.toTodoItemsUiModel(): TodoItemModelUi {
        return TodoItemModelUi(
            id = id,
            description = description,
            isDone = isDone,
            importance = importance,
            deadline = deadline,
        )
    }

}

