package com.fenix.todoapp.ui.todoItemsScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fenix.todoapp.R
import com.fenix.todoapp.ui.design.theme.backSecond
import com.fenix.todoapp.ui.design.theme.blue
import com.fenix.todoapp.ui.design.theme.label
import com.fenix.todoapp.ui.design.theme.tertiry
import com.fenix.todoapp.ui.design.theme.white
import com.fenix.todoapp.ui.todoItemsScreen.state.TodoItemModelUi


@Composable
fun ListTodoItems(
    todoItems: List<TodoItemModelUi>,
    completedCount: Int,
    isShowDone: Boolean,
    onCheckedChange: (String, Boolean) -> Unit,
    changeShowDone: (Boolean) -> Unit,
    onDelete: (String) -> Unit,
    navigateToAddTodo: (String?) -> Unit,
){

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .padding(bottom = 16.dp, end = 8.dp)
                    .size(56.dp),
                onClick = { navigateToAddTodo(null) },
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
                            onClick = { changeShowDone(!isShowDone) },
                        ) {
                            if (isShowDone){
                                Icon(
                                    modifier = Modifier
                                        .size(24.dp),
                                    painter = painterResource(id = R.drawable.visibility_off),
                                    tint = MaterialTheme.colorScheme.blue,
                                    contentDescription = ""
                                )
                            }
                            else {
                                Icon(
                                    modifier = Modifier
                                        .size(24.dp),
                                    painter = painterResource(id = R.drawable.show_hide),
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
                    items(todoItems,
                        key = {it.id}
                    ) { item ->
                        SwipeToDeleteContainer(
                            item = item,
                            onDelete = {
                                onDelete(item.id)
                            },
                            onCheckedChange = onCheckedChange,
                        ) {
                            TodoItemRow(
                                item = item,
                                onCheckedChange = onCheckedChange,
                                onClick = navigateToAddTodo
                            )
                        }
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
                                    .clickable { navigateToAddTodo(null) },
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

