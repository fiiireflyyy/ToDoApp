package com.fenix.todoapp.ui.addTodoScreen.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fenix.todoapp.R
import com.fenix.todoapp.ui.design.theme.red
import com.fenix.todoapp.ui.design.theme.tertiry

@Composable
fun DeleteRow(
    deleteTodo: () -> Unit,
    navigateBack: () -> Unit,
    canDelete: Boolean,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        IconButton(
            onClick = {
                if (canDelete){
                    deleteTodo()
                    navigateBack()
                }
            },
            enabled = canDelete
        ) {
            if (canDelete){
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.red)
            }
            else{
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.tertiry)
            }
        }
        if (canDelete){
            Text(
                text = "Удалить",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.red,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
            )
        }
        else{
            Text(
                text = "Удалить",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.tertiry,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
            )
        }


    }
}