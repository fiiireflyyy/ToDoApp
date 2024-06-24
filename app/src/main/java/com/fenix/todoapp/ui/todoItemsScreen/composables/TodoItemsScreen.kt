@file:OptIn(ExperimentalMaterial3Api::class)

package com.fenix.todoapp.ui.todoItemsScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fenix.todoapp.R
import com.fenix.todoapp.domain.model.Importance
import com.fenix.todoapp.domain.model.TodoItem
import com.fenix.todoapp.ui.design.theme.backSecond
import com.fenix.todoapp.ui.design.theme.blue
import com.fenix.todoapp.ui.design.theme.gray
import com.fenix.todoapp.ui.design.theme.green
import com.fenix.todoapp.ui.design.theme.label
import com.fenix.todoapp.ui.design.theme.lightRed
import com.fenix.todoapp.ui.design.theme.red
import com.fenix.todoapp.ui.design.theme.tertiry
import com.fenix.todoapp.ui.design.theme.white
import com.fenix.todoapp.ui.todoItemsScreen.TodoItemsScreenViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TodoItemsScreen(viewModel: TodoItemsScreenViewModel) {

    val todoItems by viewModel.todoItems.collectAsState()
    val completedCount by viewModel.completedCount.collectAsState()
    val isShowDone by viewModel.showDone.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .padding(bottom = 16.dp, end = 8.dp)
                    .size(56.dp),
                onClick = { viewModel.navigateToAddTodo() },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.blue,
                contentColor = MaterialTheme.colorScheme.white,
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add",
                )
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Spacer(
                modifier = Modifier
                    .height(60.dp),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 60.dp,
                        end = 24.dp
                    ),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Мои дела",
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        color = MaterialTheme.colorScheme.label,
                        fontSize = 32.sp,
                        lineHeight = 37.5.sp
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = "Выполнено — $completedCount",
                            color = MaterialTheme.colorScheme.tertiry,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            fontSize = 16.sp,
                            lineHeight = 20.sp
                        )
                        IconButton(
                            onClick = { viewModel.changeShowDone(isShowDone) },
                        ) {
                            if (isShowDone){
                                Icon(
                                    modifier = Modifier
                                        .size(24.dp),
                                    painter = painterResource(id = R.drawable.show_hide),
                                    tint = MaterialTheme.colorScheme.blue,
                                    contentDescription = ""
                                )
                            }
                            else {
                                Icon(
                                    modifier = Modifier
                                        .size(24.dp),
                                    painter = painterResource(id = R.drawable.visibility_off),
                                    tint = MaterialTheme.colorScheme.blue,
                                    contentDescription = ""
                                )
                            }

                        }
                    }
                }
            }
            Spacer(
                modifier = Modifier
                    .height(16.dp),
            )
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                colors = CardDefaults.elevatedCardColors(
                    contentColor = MaterialTheme.colorScheme.backSecond
                )
            ) {
                LazyColumn(
                    modifier = Modifier.background(MaterialTheme.colorScheme.backSecond)
                ) {
                    items(todoItems) { item ->
                        TodoItemRow(item = item, onCheckedChange = { updatedItem ->
                            viewModel.updateTodoItem(updatedItem)
                        },
                            onClick = { todoId ->
                                viewModel.navigateToAddTodo(todoId)
                            },
                            )
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.backSecond)
                                .padding(start = 52.dp, bottom = 20.dp),
                        ) {
                            Text(
                                modifier = Modifier
                                    .clickable { viewModel.navigateToAddTodo() },
                                text = "Новое",
                                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                fontSize = 16.sp,
                                lineHeight = 20.sp,
                                maxLines = 1,
                                color = MaterialTheme.colorScheme.tertiry,
                            )
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun TodoItemRow(
    item: TodoItem,
    onCheckedChange: (TodoItem) -> Unit,
    onClick: (String) ->Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ){
        Checkbox(
            modifier = Modifier
                .padding(start = 4.dp)
                .clip(RoundedCornerShape(2.dp)),
            checked = item.isDone,
            onCheckedChange = { isChecked -> onCheckedChange(item.copy(isDone = isChecked)) },
            colors = CheckboxColors(
                checkedCheckmarkColor = MaterialTheme.colorScheme.backSecond,
                checkedBoxColor = MaterialTheme.colorScheme.green,
                checkedBorderColor = MaterialTheme.colorScheme.green,
                uncheckedCheckmarkColor = MaterialTheme.colorScheme.backSecond,
                uncheckedBorderColor = if (item.importance is Importance.High){
                    MaterialTheme.colorScheme.red
                } else {
                    MaterialTheme.colorScheme.outline
                },
                uncheckedBoxColor = if (item.importance is Importance.High){
                    MaterialTheme.colorScheme.lightRed
                } else{
                    MaterialTheme.colorScheme.backSecond
                },
                disabledBorderColor = MaterialTheme.colorScheme.backSecond,
                disabledIndeterminateBorderColor = MaterialTheme.colorScheme.backSecond,
                disabledCheckedBoxColor = MaterialTheme.colorScheme.backSecond,
                disabledUncheckedBoxColor = MaterialTheme.colorScheme.backSecond,
                disabledIndeterminateBoxColor = MaterialTheme.colorScheme.backSecond,
                disabledUncheckedBorderColor = MaterialTheme.colorScheme.backSecond,
            )
        )
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(vertical = 12.dp),
        ){
            if(item.importance is Importance.High){
                Icon(
                    modifier = Modifier.padding(top = 2.dp, end = 4.dp),
                    painter = painterResource(id = R.drawable.hight_importance),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.red,
                )
            }
            if (item.importance is Importance.Low){
                Icon(
                    modifier = Modifier.padding(top = 2.dp),
                    painter = painterResource(id = R.drawable.low_importance),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.gray
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                Text(
                    modifier = Modifier.clickable { onClick(item.id) },
                    color = MaterialTheme.colorScheme.label,
                    style = TextStyle(
                        textDecoration = if (item.isDone){
                            TextDecoration.LineThrough
                        } else {
                            TextDecoration.None
                        }
                    ),
                    text = item.description,
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
                if (item.deadline != null){
                    Text(
                        text = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(item.deadline),
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        lineHeight = 20.sp,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.tertiry
                    )
                }
            }
        }
        IconButton(
            modifier = Modifier
                .padding(
                    end = 16.dp,
                    bottom = 12.dp,
                    start = 12.dp,
                ),
            onClick = { onClick(item.id) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_info),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.tertiry,
            )
        }
    }
}
