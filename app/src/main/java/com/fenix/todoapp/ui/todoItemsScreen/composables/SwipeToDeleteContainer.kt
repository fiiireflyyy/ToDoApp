@file:OptIn(ExperimentalMaterial3Api::class)

package com.fenix.todoapp.ui.todoItemsScreen.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.fenix.todoapp.ui.todoItemsScreen.state.TodoItemModelUi
import kotlinx.coroutines.delay

@Composable
fun SwipeToDeleteContainer(
    item: TodoItemModelUi,
    onDelete: (String) -> Unit,
    onCheckedChange: (String, Boolean) -> Unit,
    animationDuration: Int = 500,
    content: @Composable () -> Unit,
){
    var isRemoved by remember{
        mutableStateOf(false)
    }

    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when(it){
                SwipeToDismissBoxValue.EndToStart -> {
                    isRemoved = true
                }
                SwipeToDismissBoxValue.StartToEnd -> {
                    onCheckedChange(item.id, true)
                }
                SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
            }
            return@rememberSwipeToDismissBoxState true
        }
    )

    LaunchedEffect(key1 = isRemoved) {
        if(isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item.id)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismissBox(
            state = state,
            backgroundContent = {
                DeleteBackGround(swipeDismissState = state, item.isDone)
            },
            content = {content()},
            enableDismissFromStartToEnd = !item.isDone
        )
    }
}