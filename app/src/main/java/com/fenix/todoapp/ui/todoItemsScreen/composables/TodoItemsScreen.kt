@file:OptIn(ExperimentalMaterial3Api::class)

package com.fenix.todoapp.ui.todoItemsScreen.composables

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fenix.todoapp.ui.design.theme.ProgressBar
import com.fenix.todoapp.ui.design.theme.green
import com.fenix.todoapp.ui.design.theme.red
import com.fenix.todoapp.ui.design.theme.white
import com.fenix.todoapp.ui.todoItemsScreen.TodoItemsScreenViewModel
import com.fenix.todoapp.ui.todoItemsScreen.state.TodoItemModelUi
import com.fenix.todoapp.ui.todoItemsScreen.state.TodoItemsScreenState
import com.fenix.todoapp.ui.todoItemsScreen.state.TodoItemsScreenUiEffects
import kotlinx.coroutines.delay
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
        is TodoItemsScreenState.Error -> showUpdateBtn(
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
fun showUpdateBtn(
    onClick: ()->Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        ){
        Button(onClick = onClick) {
        }
    }
}
