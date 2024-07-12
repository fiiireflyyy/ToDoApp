@file:OptIn(ExperimentalMaterial3Api::class)

package com.fenix.todoapp.ui.todoItemsScreen.composables

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fenix.todoapp.ui.design.theme.ProgressBar
import com.fenix.todoapp.ui.design.theme.label
import com.fenix.todoapp.ui.design.theme.white
import com.fenix.todoapp.ui.todoItemsScreen.TodoItemsScreenViewModel
import com.fenix.todoapp.ui.todoItemsScreen.state.TodoItemsScreenState
import com.fenix.todoapp.ui.todoItemsScreen.state.TodoItemsScreenUiEffects
import kotlinx.coroutines.flow.Flow

@Composable
fun TodoItemsScreen(viewModel: TodoItemsScreenViewModel) {

    val todoItemsScreenState = viewModel.todoItemsScreenUiState.collectAsStateWithLifecycle().value
    ShowUiEffectsIfNeeded(uiEffectFlow = viewModel.uiEffectFlow)

    when (todoItemsScreenState) {
        is TodoItemsScreenState.Loading -> ProgressBar()
        is TodoItemsScreenState.Success -> ListTodoItems(
            todoItems = todoItemsScreenState.todoItems,
            completedCount = todoItemsScreenState.completedCount,
            isShowDone = todoItemsScreenState.isShowDone,
            onCheckedChange = viewModel::updateTodoItem,
            changeShowDone = viewModel::changeShowDone,
            onDelete = viewModel::deleteTodo,
            navigateToAddTodo = viewModel::navigateToAddTodo,
        )
        is TodoItemsScreenState.Error -> ShowUpdateBtn(
            onClick = viewModel::getListFromBase
        )
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
@Composable
fun ShowUpdateBtn(
    onClick: ()->Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Что-то пошло не так...",
                color = MaterialTheme.colorScheme.label)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onClick,
            ) {
                Text(text = "Обновить", color = MaterialTheme.colorScheme.white)
            }
        }
    }
}
