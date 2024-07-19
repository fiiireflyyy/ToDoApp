package com.fenix.todoapp.navigation

import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.fenix.todoapp.R
import com.fenix.todoapp.di.activity.MainActivityScope
import com.fenix.todoapp.ui.MainActivity
import com.fenix.todoapp.ui.addTodoScreen.AddTodoFragment
import com.fenix.todoapp.ui.todoItemsScreen.TodoItemsFragment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Provider
@MainActivityScope
class NavManager @Inject constructor(
    private val activity: Provider<MainActivity>,
) {

    private val _todoItemId = MutableStateFlow<String?>(null)
    val todoItemId = _todoItemId.asStateFlow()

    private val fragmentManager
        get() = activity.get().supportFragmentManager.takeIf { it.isDestroyed.not() }

    fun navigateToAddFragment(id: String?) {
        _todoItemId.value = id
        fragmentManager?.commit {
            replace(R.id.fragment_container, AddTodoFragment())
            addToBackStack(null)
        }
    }

    fun navigateBack(){
        fragmentManager?.popBackStack()
    }

    fun navigateToMainScreen() {
        fragmentManager?.commit {
            replace(R.id.fragment_container, TodoItemsFragment())
        }
    }
}