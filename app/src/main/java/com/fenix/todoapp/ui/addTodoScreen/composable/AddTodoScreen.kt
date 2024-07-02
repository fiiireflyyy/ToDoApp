@file:OptIn(ExperimentalMaterial3Api::class)

import android.app.DatePickerDialog
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fenix.todoapp.R
import com.fenix.todoapp.ui.addTodoScreen.AddTodoScreenViewModel
import com.fenix.todoapp.ui.addTodoScreen.composable.ImportanceDropdown
import com.fenix.todoapp.ui.addTodoScreen.state.AddTodoScreenState
import com.fenix.todoapp.ui.design.theme.ProgressBar
import com.fenix.todoapp.ui.design.theme.backSecond
import com.fenix.todoapp.ui.design.theme.blue
import com.fenix.todoapp.ui.design.theme.blueTray
import com.fenix.todoapp.ui.design.theme.label
import com.fenix.todoapp.ui.design.theme.overlay
import com.fenix.todoapp.ui.design.theme.red
import com.fenix.todoapp.ui.design.theme.tertiry
import com.fenix.todoapp.ui.design.theme.white
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun AddTodoScreen(
    viewModel: AddTodoScreenViewModel,
    ) {
    val todoUiState = viewModel.uiState.collectAsState().value
    when (todoUiState) {
        is AddTodoScreenState.Loading -> ProgressBar()
        is AddTodoScreenState.Success -> DetailsTodo(viewModel)
        is AddTodoScreenState.Error -> {}
    }

}
@Composable
fun DetailsTodo(
    viewModel: AddTodoScreenViewModel,
) {
    val description by viewModel.description.collectAsState()
    val importance by viewModel.importance.collectAsState()
    val deadline by viewModel.deadline.collectAsState()
    val canDelete by viewModel.canDelete.collectAsState()
    var showDatePicker by remember { mutableStateOf(false) }
    var switchState by remember { mutableStateOf(false) }

    val topAppBarState = rememberTopAppBarState()
    val behavior = TopAppBarDefaults.pinnedScrollBehavior(
        state = topAppBarState,
    )
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .shadow(if (scrollState.value == 0) 0.dp else 8.dp)
                    .fillMaxWidth()
                    .nestedScroll(behavior.nestedScrollConnection),
                navigationIcon = {
                    IconButton(onClick = { viewModel.navigateBack() }) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.label,
                        )
                    }
                },
                title = {
                    Row{
                        Spacer(modifier = Modifier.weight(1f))
                        TextButton(
                            colors = ButtonDefaults.buttonColors(
                                contentColor = MaterialTheme.colorScheme.blue,
                                containerColor = MaterialTheme.colorScheme.background,
                            ),
                            onClick = {
                                viewModel.changeTodoItem()
                                viewModel.navigateBack()
                            }
                        ) {
                            Text(
                                text = "СОХРАНИТЬ",
                                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                fontSize = 14.sp,
                                lineHeight = 24.sp,
                            )
                        }
                    }
                },
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.blue,
                    scrolledContainerColor = MaterialTheme.colorScheme.background,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.blue
                ),
                scrollBehavior = behavior,
            )
        })
    { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                )
                .verticalScroll(scrollState)
                .background(MaterialTheme.colorScheme.background)
        ) {
            ElevatedCard(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .padding(paddingValues),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.white
                )
            ) {

                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = description,
                    textStyle = TextStyle(
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontSize = 16.sp,
                        lineHeight = 18.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                    ),
                    onValueChange = { viewModel.setDescription(it) },
                    placeholder = {
                        Text(
                            text = "Что надо сделать...",
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            color = MaterialTheme.colorScheme.tertiry,
                            fontSize = 16.sp,
                            lineHeight = 18.sp,
                        )
                    },
                    minLines = 3,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.backSecond,
                        focusedIndicatorColor = MaterialTheme.colorScheme.backSecond,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.backSecond,
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 16.dp,
                        start = 16.dp,
                        top = 16.dp,
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Важность",
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    color = MaterialTheme.colorScheme.label,
                    fontSize = 16.sp,
                )
                ImportanceDropdown(
                    importance = importance,
                    onImportanceChange = { viewModel.setImportance(it) }
                )

            }


            Divider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .padding(
                            bottom = 16.dp,
                            start = 16.dp,
                            top = 16.dp,
                        ),
                ) {
                    Text(
                        text = "Сделать до",
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        color = MaterialTheme.colorScheme.label,
                        fontSize = 16.sp,
                    )
                    if (deadline != null) {
                        Text(
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .clickable { showDatePicker = true },
                            text = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(deadline!!),
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            color = MaterialTheme.colorScheme.blue,
                        )
                    }
                }
                Switch(
                    checked = deadline!=null || switchState,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.blue,
                        checkedTrackColor = MaterialTheme.colorScheme.blueTray,
                        uncheckedThumbColor = MaterialTheme.colorScheme.white,
                        uncheckedTrackColor = MaterialTheme.colorScheme.overlay,
                    ),
                    onCheckedChange = {
                        Log.d("mytag", it.toString())
                        if (it) {
                            switchState = true
                            showDatePicker = true
                        } else {
                            showDatePicker = false
                            switchState = false
                            viewModel.setDeadline(null)
                        }
                    }
                )
            }
            Divider()
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
                            viewModel.deleteTodo()
                            viewModel.navigateBack()
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
        if (showDatePicker) {
            DatePickerDialog(onDateSelected = { date ->
                viewModel.setDeadline(date)
                showDatePicker = false
            }, onDismissRequest = {
                switchState = false
                showDatePicker = false
            })
        }
    }
}


@Composable
fun DatePickerDialog(onDateSelected: (Date) -> Unit, onDismissRequest: () -> Unit){
    val context = LocalContext.current
    val calendar = Calendar.getInstance()



    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            onDateSelected(calendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    datePickerDialog.setOnDismissListener {
        onDismissRequest()
    }

    datePickerDialog.show()
}