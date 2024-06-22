package com.fenix.todoapp.ui.todoItemsScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.fenix.todoapp.data.repository.TodoItemsRepository
import com.fenix.todoapp.domain.model.TodoItem
import com.fenix.todoapp.navigation.Screen
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
    private val _todoItems = MutableStateFlow<List<TodoItem>>(emptyList())
    val todoItems = _todoItems.asStateFlow()


    private val _completedCount = MutableStateFlow(0)
    val completedCount = _completedCount.asStateFlow()

    private val _showDone = MutableStateFlow(true)
    val showDone = _showDone.asStateFlow()


    init{
        loadTodoItems()
        Log.d("mytag","создалась 1234")
    }

    private fun loadTodoItems(){
        viewModelScope.launch {
            repository.todoItems.collect{
                _todoItems.value = it
                _completedCount.value = todoItems.value.count { it.isDone }
                if(!showDone.value){
                    _todoItems.value = _todoItems.value.filter { !it.isDone }
                }
            }
        }
    }

    fun changeShowDone(value: Boolean){
        _showDone.value = !value
        loadTodoItems()

    }

    fun navigateToAddTodo(){
        navController.navigate("${Screen.AddTodoScreen.route}/new")
    }

    fun navigateToAddTodo(id: String){
        navController.navigate("${Screen.AddTodoScreen.route}/${id}")
    }

    fun updateTodoItem(item: TodoItem){
        repository.updateTodoItem(item)
        loadTodoItems()
    }
}