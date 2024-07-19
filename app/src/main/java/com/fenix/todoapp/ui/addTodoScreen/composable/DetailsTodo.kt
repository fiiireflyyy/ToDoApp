package com.fenix.todoapp.ui.addTodoScreen.composable

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fenix.todoapp.R
import com.fenix.todoapp.domain.model.Importance
import com.fenix.todoapp.ui.addTodoScreen.AddTodoScreenViewModel
import com.fenix.todoapp.ui.design.theme.blue
import com.fenix.todoapp.ui.design.theme.blueTray
import com.fenix.todoapp.ui.design.theme.label
import com.fenix.todoapp.ui.design.theme.overlay
import com.fenix.todoapp.ui.design.theme.white
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTodo(viewModel: AddTodoScreenViewModel) {
    val description by viewModel.description.collectAsStateWithLifecycle()
    val importance by viewModel.importance.collectAsStateWithLifecycle()
    val deadline by viewModel.deadline.collectAsStateWithLifecycle()
    val canDelete by viewModel.canDelete.collectAsStateWithLifecycle()
    var showDatePicker by remember { mutableStateOf(false) }
    var switchState by remember { mutableStateOf(false) }

    val topAppBarState = rememberTopAppBarState()
    val behavior = TopAppBarDefaults.pinnedScrollBehavior(state = topAppBarState)
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = { DetailsTopAppBar(viewModel, scrollState, behavior) },
        content = { paddingValues ->
            DetailsContent(
                viewModel,
                description,
                importance,
                deadline,
                canDelete,
                paddingValues,
                scrollState,
                switchState,
                showDatePicker,
                onSwitchStateChange = { switchState = it },
                onShowDatePickerChange = { showDatePicker = it }
            )
        }
    )

    if (showDatePicker) {
        DatePickerDialogc(onDateSelected = { date ->
            viewModel.setDeadline(date)
            showDatePicker = false
        }, onDismissRequest = {
            switchState = false
            showDatePicker = false
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopAppBar(viewModel: AddTodoScreenViewModel, scrollState: ScrollState, behavior: TopAppBarScrollBehavior) {
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
            Row {
                Spacer(modifier = Modifier.weight(1f))
                TextButton(
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.blue,
                        containerColor = MaterialTheme.colorScheme.background,
                    ),
                    onClick = viewModel::changeTodoItem
                ) {
                    Text(
                        text = "СОХРАНИТЬ",
                        style = MaterialTheme.typography.bodyLarge,
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
}

@Composable
fun DetailsContent(
    viewModel: AddTodoScreenViewModel,
    description: String,
    importance: Importance,
    deadline: Long?,
    canDelete: Boolean,
    paddingValues: PaddingValues,
    scrollState: ScrollState,
    switchState: Boolean,
    showDatePicker: Boolean,
    onSwitchStateChange: (Boolean) -> Unit,
    onShowDatePickerChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.background)
    ) {
        CustomCard(
            paddingValues = paddingValues,
            description = description,
            setDescription = viewModel::setDescription
        )
        ImportanceRow(importance, viewModel::setImportance)
        Divider()
        DeadlineRow(deadline, switchState, showDatePicker, onSwitchStateChange, onShowDatePickerChange, viewModel::setDeadline)
        DeleteRow(
            deleteTodo = { viewModel.deleteTodo() },
            navigateBack = { viewModel.navigateBack() },
            canDelete = canDelete,
        )
    }
}

@Composable
fun ImportanceRow(importance: Importance, setImportance: (Importance) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, start = 16.dp, top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Важность",
            color = MaterialTheme.colorScheme.label,
            style = MaterialTheme.typography.bodyMedium,
        )
        ImportanceDropdown(
            importance = importance,
            onImportanceChange = setImportance
        )
    }
}

@Composable
fun DeadlineRow(
    deadline: Long?,
    switchState: Boolean,
    showDatePicker: Boolean,
    onSwitchStateChange: (Boolean) -> Unit,
    onShowDatePickerChange: (Boolean) -> Unit,
    setDeadline: (Long?) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 16.dp, start = 16.dp, top = 16.dp),
        ) {
            Text(
                text = "Сделать до",
                color = MaterialTheme.colorScheme.label,
                style = MaterialTheme.typography.bodyMedium
            )
            if (deadline != null) {
                Text(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .clickable { onShowDatePickerChange(true) },
                    text = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(deadline),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.blue,
                )
            }
        }
        Switch(
            checked = deadline != null || switchState,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.blue,
                checkedTrackColor = MaterialTheme.colorScheme.blueTray,
                uncheckedThumbColor = MaterialTheme.colorScheme.white,
                uncheckedTrackColor = MaterialTheme.colorScheme.overlay,
            ),
            onCheckedChange = {
                if (it) {
                    onSwitchStateChange(true)
                    onShowDatePickerChange(true)
                } else {
                    onSwitchStateChange(false)
                    onShowDatePickerChange(false)
                    setDeadline(null)
                }
            }
        )
    }
}
