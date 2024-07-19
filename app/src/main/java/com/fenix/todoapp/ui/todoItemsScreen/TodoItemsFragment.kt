package com.fenix.todoapp.ui.todoItemsScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.fenix.todoapp.di.todoItemsScreen.TodoItemsScreenComponent
import com.fenix.todoapp.ui.MainActivity
import com.fenix.todoapp.ui.design.theme.ToDoTheme
import com.fenix.todoapp.ui.design.theme.ToDoThemeWithUserChoice
import com.fenix.todoapp.ui.todoItemsScreen.composables.TodoItemsScreen
import javax.inject.Inject

class TodoItemsFragment : Fragment() {

    private lateinit var component: TodoItemsScreenComponent

    @Inject
    lateinit var viewModel: TodoItemsScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = (activity as MainActivity)
            .mainActivityComponent
            .todoItemsFragmentComponent()
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val userThemeChoice = viewModel.userThemeChoice.collectAsState().value
                ToDoThemeWithUserChoice(userThemeChoice = userThemeChoice) {
                    TodoItemsScreen(viewModel = viewModel)
                }
            }
        }
    }
}