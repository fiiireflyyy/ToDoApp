package com.fenix.todoapp.ui.addTodoScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.fenix.todoapp.data.Result
import com.fenix.todoapp.data.repository.TodoItemsRepository
import com.fenix.todoapp.di.addTodoScreen.AddTodoScope
import com.fenix.todoapp.domain.mapper.TodoToPostMapper
import com.fenix.todoapp.domain.model.Importance
import com.fenix.todoapp.domain.model.TodoItem
import com.fenix.todoapp.navigation.NavManager
import com.fenix.todoapp.navigation.Screen
import com.fenix.todoapp.ui.addTodoScreen.state.AddTodoScreenState
import com.fenix.todoapp.ui.todoItemsScreen.state.TodoItemsScreenUiEffects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.Date
import javax.inject.Inject

/**
 * [AddTodoScreenViewModel] is responsible for managing the UI-related data for the Add Todo screen.
 */
@AddTodoScope
class AddTodoScreenViewModel @Inject constructor(
    private val repository: TodoItemsRepository,
    private val navManager: NavManager,
) : ViewModel() {

    private val _importance = MutableStateFlow<Importance>(Importance.Medium)
    val importance = _importance.asStateFlow()

    private val _description = MutableStateFlow<String>("")
    val description = _description.asStateFlow()

    private val _deadline = MutableStateFlow<Date?>(null)
    val deadline = _deadline.asStateFlow()

    private var _canDelete = MutableStateFlow(false)
    val canDelete = _canDelete.asStateFlow()


    private val _uiState = MutableStateFlow<AddTodoScreenState>(AddTodoScreenState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiEffectFlow = MutableSharedFlow<TodoItemsScreenUiEffects>()
    val uiEffectFlow = _uiEffectFlow.asSharedFlow()

    private var todoItem: TodoItem? = null

    init {
        getChangeItem()
    }

    private fun getChangeItem() {
        viewModelScope.launch {
            navManager.todoItemId.collect { id ->
                if (id != null) {
                    Log.d("TESTLOG","my id${id}")
                    when (val result = repository.getItemById(id)) {
                        is Result.Success -> {
                            _uiState.value = AddTodoScreenState.Success
                            val currentTodoItem = result.data
                            todoItem = currentTodoItem
                            _canDelete.value = true
                            _importance.value = currentTodoItem.importance
                            _description.value = currentTodoItem.description
                            _deadline.value = currentTodoItem.deadline
                        }

                        is Result.Error -> {
                            _uiState.value = AddTodoScreenState.Error(result.e.toString())
                        }
                    }
                } else {
                    _uiState.value = AddTodoScreenState.Success
                    _canDelete.value = false
                }
            }
        }
    }

    fun setDescription(value: String) {
        _description.value = value
    }

    fun setImportance(value: Importance) {
        _importance.value = value
    }

    fun setDeadline(value: Date?) {
        _deadline.value = value
    }

    fun changeTodoItem() {
        viewModelScope.launch {
            val currentTodoItem = todoItem
            if (currentTodoItem == null) {
                val newItem = TodoItem(
                    id = LocalDateTime.now().toString(),
                    description = description.value,
                    importance = importance.value,
                    isDone = false,
                    creationDate = LocalDateTime.now(),
                    deadline = deadline.value
                )
                _uiState.value = AddTodoScreenState.Loading

                when (val result = repository.addTodoItem(newItem)) {
                    is Result.Success -> navigateBack()
                    is Result.Error -> {
                        val errorMessage = result.e.message
                        if (errorMessage == null) {
                            _uiEffectFlow.emit(TodoItemsScreenUiEffects.SomethingWentWrongMessage)
                        } else {
                            _uiEffectFlow.emit(TodoItemsScreenUiEffects.CustomMessage(errorMessage))
                        }
                        _uiState.value = AddTodoScreenState.Success
                    }
                }
            } else {
                _uiState.value = AddTodoScreenState.Loading
                todoItem = currentTodoItem.copy(
                    description = description.value,
                    importance = importance.value,
                    deadline = deadline.value,
                    changeDate = LocalDateTime.now()
                )
                when (val result = repository.changeTodoItem(todoItem)) {
                    is Result.Success -> navigateBack()
                    is Result.Error -> {
                        val errorMessage = result.e.message
                        if (errorMessage == null) {
                            _uiEffectFlow.emit(TodoItemsScreenUiEffects.SomethingWentWrongMessage)
                        } else {
                            _uiEffectFlow.emit(TodoItemsScreenUiEffects.CustomMessage(errorMessage))
                        }
                        _uiState.value = AddTodoScreenState.Success
                    }
                }
            }
        }

    }

    fun deleteTodo() {
        viewModelScope.launch {
            navManager.todoItemId.collect { id ->
                if (id != null)
                    repository.deleteTodo(id)
            }

        }
    }

    fun navigateBack() {
        navManager.navigateBack()
    }

}