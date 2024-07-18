package com.fenix.todoapp.ui.todoItemsScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.fenix.todoapp.data.Result
import com.fenix.todoapp.data.repository.TodoItemsRepository
import com.fenix.todoapp.di.todoItemsScreen.TodoItemsScope
import com.fenix.todoapp.domain.model.TodoItem
import com.fenix.todoapp.navigation.NavManager
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton
/**
 * [TodoItemsScreenViewModel] is responsible for managing the UI-related data for the Todo screen.
 */

@TodoItemsScope
class TodoItemsScreenViewModel @Inject constructor(
    private val repository: TodoItemsRepository,
    private val navManager: NavManager,
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
    }

    fun getListFromBase() {
        viewModelScope.launch {
            repository.getTodoItems()
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
        navManager.navigateToAddFragment(id)
    }

    fun updateTodoItem(id: String, isDone: Boolean) {
        val currentDateMillis = Calendar.getInstance().timeInMillis
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.updateCompleted(
                id =  id,
                isCompleted = isDone,
                dateOfChange = currentDateMillis,
            )
            if (result.isFailure){
                _uiEffectFlow.emit(
                    TodoItemsScreenUiEffects.CustomMessage("изменено локально")
                )
            }
        }
    }

    fun deleteTodo(todoId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.deleteTodoItem(todoId)
            if (result.isFailure){
                _uiEffectFlow.emit(
                    TodoItemsScreenUiEffects.CustomMessage("изменено локально")
                )
            }
        }
    }

    private fun TodoItem.toTodoItemsUiModel(): TodoItemModelUi {
        return TodoItemModelUi(
            id = id,
            description = text,
            isDone = isCompleted,
            importance = importance,
            deadline = DateFormatting.toFormattedDate(deadline),
        )
    }

    object DateFormatting {

        private const val DATE_PATTERN = "dd MMM yyyy"
        @SuppressLint("ConstantLocale")
        private val formatter = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())

        fun toFormattedDate(dateLong: Long?): String? =
            dateLong?.let { formatter.format(Date(it)) }

        fun toDateLong(dateString: String?): Long? =
            dateString?.let { formatter.parse(it)?.time ?: 0L }

    }

}

