@file:OptIn(ExperimentalMaterial3Api::class)

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import com.fenix.todoapp.ui.addTodoScreen.AddTodoScreenViewModel
import com.fenix.todoapp.ui.addTodoScreen.state.AddTodoScreenState
import com.fenix.todoapp.ui.design.theme.ProgressBar
import com.fenix.todoapp.ui.addTodoScreen.composable.DetailsTodo
import com.fenix.todoapp.ui.todoItemsScreen.state.TodoItemsScreenUiEffects
import kotlinx.coroutines.flow.Flow

@Composable
fun AddTodoScreen(
    viewModel: AddTodoScreenViewModel,
    )
{
    val todoUiState = viewModel.uiState.collectAsState().value
    ShowUiEffectsIfNeeded(uiEffectFlow = viewModel.uiEffectFlow)

    when (todoUiState) {
        is AddTodoScreenState.Loading -> ProgressBar()
        is AddTodoScreenState.Success -> DetailsTodo(viewModel)
        is AddTodoScreenState.Error -> {}
    }

}

@Composable
private fun ShowUiEffectsIfNeeded(uiEffectFlow: Flow<TodoItemsScreenUiEffects>) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        uiEffectFlow.collect { uiEffect ->
            when (uiEffect) {
                is TodoItemsScreenUiEffects.SomethingWentWrongMessage -> {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
                is TodoItemsScreenUiEffects.CustomMessage -> {
                    Toast.makeText(context, uiEffect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}