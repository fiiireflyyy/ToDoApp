package com.fenix.todoapp.ui.addTodoScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.fenix.todoapp.data.Result
import com.fenix.todoapp.data.repository.TodoItemsRepository
import com.fenix.todoapp.domain.model.Importance
import com.fenix.todoapp.domain.model.TodoItem
import com.fenix.todoapp.navigation.Screen
import com.fenix.todoapp.ui.addTodoScreen.state.AddTodoScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.Date
import javax.inject.Inject

class AddTodoScreenViewModel @Inject constructor(
    private val repository: TodoItemsRepository,
    private val navController: NavController,
    ) : ViewModel() {

    private val _importance = MutableStateFlow<Importance>(Importance.Medium)
    val importance = _importance.asStateFlow()


    private val _description = MutableStateFlow<String>("")
    val description = _description.asStateFlow()

    private val _deadline = MutableStateFlow<Date?>(null)
    val deadline = _deadline.asStateFlow()


    private var _canDelete = MutableStateFlow(false)
    val canDelete = _canDelete.asStateFlow()

    private val todoId: String? = navController
        .getBackStackEntry("${Screen.AddTodoScreen.route}/{todoid}")
        .arguments
        ?.getString("todoid")

    private val _uiState = MutableStateFlow<AddTodoScreenState>(AddTodoScreenState.Loading)
    val uiState = _uiState.asStateFlow()

    private var todoItem: TodoItem? = null

    init {
        getChangeItem()
    }

    private fun getChangeItem(){
        if (todoId !=null){
            viewModelScope.launch(Dispatchers.IO) {
                repository.getItemById(todoId).collect{result ->
                    when(result){
                        is Result.Success -> {
                            _uiState.value = AddTodoScreenState.Success
                            todoItem = result.data
                            _canDelete.value = true
                            _importance.value = todoItem!!.importance
                            _description.value = todoItem!!.description
                            _deadline.value = todoItem!!.deadline
                        }
                        is Result.Loading -> {
                            _uiState.value = AddTodoScreenState.Loading
                        }
                        is Result.Error -> {
                            _uiState.value = AddTodoScreenState.Error(result.e.toString())
                        }
                    }
                }
            }
        }
        else{
            _uiState.value = AddTodoScreenState.Success
            _canDelete.value = false
        }
    }

    fun setDescription(value: String){
        _description.value = value
    }

    fun setImportance(value: Importance){
        _importance.value = value
    }

    fun setDeadline(value: Date?){
        _deadline.value = value
    }

    fun changeTodoItem(){
        viewModelScope.launch(Dispatchers.IO) {
            if (todoItem == null){
                val newItem = TodoItem(
                    id = LocalDateTime.now().toString(),
                    description = description.value,
                    importance = importance.value,
                    isDone = false,
                    creationDate = LocalDateTime.now(),
                    deadline = deadline.value
                )
                repository.addTodoItem(newItem)
            }
            else{
                todoItem = todoItem!!.copy(
                    description = description.value,
                    importance = importance.value,
                    deadline = deadline.value,
                    changeDate = LocalDateTime.now()
                )
                repository.changeTodoItem(todoItem)
            }
        }

    }

    fun deleteTodo(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTodo(todoId!!)
        }
    }

    fun navigateBack(){
        navController.popBackStack()
    }

}