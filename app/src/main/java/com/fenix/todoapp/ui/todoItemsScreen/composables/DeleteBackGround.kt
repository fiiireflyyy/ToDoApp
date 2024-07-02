@file:OptIn(ExperimentalMaterial3Api::class)

package com.fenix.todoapp.ui.todoItemsScreen.composables

import android.util.Log
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fenix.todoapp.ui.design.theme.green
import com.fenix.todoapp.ui.design.theme.red
import com.fenix.todoapp.ui.design.theme.white

@Composable
fun DeleteBackGround(
    swipeDismissState: SwipeToDismissBoxState,
    isDone: Boolean,
){
    val color = when(swipeDismissState.dismissDirection){
        SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.red
        SwipeToDismissBoxValue.StartToEnd -> if(!isDone){
            MaterialTheme.colorScheme.green
        } else{
            Color.Transparent
        }
        SwipeToDismissBoxValue.Settled -> Color.Transparent
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(16.dp),
    ){
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(color),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ){
            Icon(
                Icons.Default.Check,
                modifier = Modifier
                    .padding(
                        start = 24.dp,
                    )
                    .size(24.dp),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.white,
            )
            Spacer(modifier = Modifier)
            Icon(
                Icons.Default.Delete,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.white,
                modifier = Modifier
                    .padding(
                        end = 24.dp,
                    )
                    .size(24.dp),
            )
        }
    }
}
