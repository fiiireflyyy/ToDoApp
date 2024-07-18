package com.fenix.todoapp.ui.addTodoScreen

import AddTodoScreen
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.fenix.todoapp.di.addTodoScreen.AddTodoScreenComponent
import com.fenix.todoapp.ui.MainActivity
import com.fenix.todoapp.ui.design.theme.ToDoTheme
import javax.inject.Inject

class AddTodoFragment : Fragment() {

    private lateinit var component: AddTodoScreenComponent

    @Inject
    lateinit var viewModel: AddTodoScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = (activity as MainActivity)
            .mainActivityComponent
            .todoItemsAddFragmentComponent()
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ToDoTheme {
                    AddTodoScreen(viewModel = viewModel)
                }
            }
        }
    }
}