package com.fenix.todoapp.ui.settingsThemeScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.fenix.todoapp.di.settingsThemeScreen.SettingsThemeScreenComponent
import com.fenix.todoapp.ui.MainActivity
import com.fenix.todoapp.ui.design.theme.ToDoThemeWithUserChoice
import com.fenix.todoapp.ui.settingsThemeScreen.composables.SettingsThemeScreen
import javax.inject.Inject

class SettingsThemeFragment : Fragment() {

    private lateinit var component: SettingsThemeScreenComponent

    @Inject
    lateinit var viewModel: SettingsThemeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = (activity as MainActivity)
            .mainActivityComponent
            .settingsThemeFragmentComponent()
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val userThemeChoice = viewModel.userThemeChoice.collectAsState().value
                ToDoThemeWithUserChoice(userThemeChoice = userThemeChoice) {
                    SettingsThemeScreen(viewModel = viewModel)
                }
            }
        }
    }
}