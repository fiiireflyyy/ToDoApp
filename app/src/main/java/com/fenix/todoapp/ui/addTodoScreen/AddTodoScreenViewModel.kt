package com.fenix.todoapp.ui.addTodoScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.fenix.todoapp.data.repository.TodoItemsRepository
import com.fenix.todoapp.domain.model.Importance
import com.fenix.todoapp.domain.model.TodoItem
import com.fenix.todoapp.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        ?.getString("todoid") ?: null

    private var todoItem: TodoItem? = null

    init {
        getChangeItem()
    }

    private fun getChangeItem(){
        if (todoId !=null && todoId !="new"){
            todoItem = repository.getTodoItemById(todoId)
            _canDelete.value = true
            if (todoItem !=null){
                _importance.value = todoItem!!.importance
                _description.value = todoItem!!.description
                _deadline.value = todoItem!!.deadline
            }
        }
        else{
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
            repository.updateTodoItem(todoItem)
        }
    }

    fun deleteTodo(){
        repository.deleteTodo(todoId!!)
    }

    fun navigateBack(){
        navController.popBackStack()
    }


    override fun onCleared() {
        super.onCleared()
        Log.d("mytag","смерть")
    }
}