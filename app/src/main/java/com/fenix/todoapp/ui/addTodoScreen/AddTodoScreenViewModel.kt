package com.fenix.todoapp.ui.addTodoScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fenix.todoapp.data.preferences.PreferencesManager
import com.fenix.todoapp.data.repository.TodoItemsRepository
import com.fenix.todoapp.di.addTodoScreen.AddTodoScope
import com.fenix.todoapp.domain.model.Importance
import com.fenix.todoapp.domain.model.SettingsTheme
import com.fenix.todoapp.domain.model.TodoItem
import com.fenix.todoapp.navigation.NavManager
import com.fenix.todoapp.ui.addTodoScreen.state.AddTodoScreenState
import com.fenix.todoapp.ui.todoItemsScreen.state.TodoItemsScreenUiEffects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.Calendar
import javax.inject.Inject

/**
 * [AddTodoScreenViewModel] is responsible for managing the UI-related data for the Add Todo screen.
 */
@AddTodoScope
class AddTodoScreenViewModel @Inject constructor(
    private val repository: TodoItemsRepository,
    private val navManager: NavManager,
    private val preferencesManager: PreferencesManager,
    ) : ViewModel() {

    private val _importance = MutableStateFlow<Importance>(Importance.Medium)
    val importance = _importance.asStateFlow()

    private val _description = MutableStateFlow<String>("")
    val description = _description.asStateFlow()

    private val _deadline = MutableStateFlow<Long?>(null)
    val deadline = _deadline.asStateFlow()

    private var _canDelete = MutableStateFlow(false)
    val canDelete = _canDelete.asStateFlow()


    private val _uiState = MutableStateFlow<AddTodoScreenState>(AddTodoScreenState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiEffectFlow = MutableSharedFlow<TodoItemsScreenUiEffects>()
    val uiEffectFlow = _uiEffectFlow.asSharedFlow()

    private var todoItem: TodoItem? = null

    private val _userThemeChoice =
        MutableStateFlow<SettingsTheme>(SettingsTheme.SystemThemeChoice)
    val userThemeChoice = _userThemeChoice.asStateFlow()

    init {
        getChangeItem()
        collectUserThemeChoice()
    }

    private fun getChangeItem() {
        viewModelScope.launch {
            navManager.todoItemId.collect { id ->
                if (id != null) {
                    val result = repository.getItem(id)
                    if (result.isSuccess) {
                        _uiState.value = AddTodoScreenState.Success
                        val currentTodoItem = result.getOrThrow()
                        todoItem = currentTodoItem
                        _canDelete.value = true
                        _importance.value = currentTodoItem.importance
                        _description.value = currentTodoItem.text
                        _deadline.value = currentTodoItem.deadline


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

    fun setDeadline(value: Long?) {
        _deadline.value = value
    }

    fun changeTodoItem() {
        viewModelScope.launch {
            val currentDateMillis = Calendar.getInstance().timeInMillis
            val currentTodoItem = todoItem
            if (currentTodoItem == null) {
                val newItem = TodoItem(
                    id = LocalDateTime.now().toString(),
                    text = description.value,
                    importance = importance.value,
                    isCompleted = false,
                    dateOfChange = currentDateMillis,
                    deadline = deadline.value,
                    dateOfCreation = currentDateMillis,
                )
                _uiState.value = AddTodoScreenState.Loading

                val result = repository.addTodoItem(newItem)

                if (result.isSuccess) {
                    navigateBack()
                } else {
                    _uiEffectFlow.emit(TodoItemsScreenUiEffects.CustomMessage("Локально"))
                }

                _uiState.value = AddTodoScreenState.Success
            } else {
                _uiState.value = AddTodoScreenState.Loading
                todoItem = currentTodoItem.copy(
                    text = description.value,
                    importance = importance.value,
                    deadline = deadline.value,
                    dateOfChange = currentDateMillis
                )
                val result = repository.updateTodoItem(todoItem!!)

                if (result.isSuccess) {
                    navigateBack()
                } else {
                    _uiEffectFlow.emit(TodoItemsScreenUiEffects.CustomMessage("Локально"))
                }
                _uiState.value = AddTodoScreenState.Success
            }
        }
    }

    fun deleteTodo() {
        viewModelScope.launch {
            navManager.todoItemId.collect { id ->
                if (id != null) {
                    val result = repository.deleteTodoItem(id)
                    if (result.isSuccess) {
                        navigateBack()
                    } else {
                        _uiEffectFlow.emit(TodoItemsScreenUiEffects.CustomMessage("Локально"))
                    }
                }
            }
        }
    }

    fun navigateBack() {
        navManager.navigateBack()
    }

    private fun collectUserThemeChoice() {
        viewModelScope.launch(Dispatchers.Default) {
            preferencesManager.selectedUserThemeChoice.collect {
                _userThemeChoice.value = it
            }
        }
    }

}