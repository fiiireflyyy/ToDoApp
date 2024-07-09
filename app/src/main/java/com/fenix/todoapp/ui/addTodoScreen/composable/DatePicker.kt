package com.fenix.todoapp.ui.addTodoScreen.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar
import java.util.Date

@Composable
fun DatePickerDialogc(onDateSelected: (Date) -> Unit, onDismissRequest: () -> Unit){
    val context = LocalContext.current
    val calendar = Calendar.getInstance()



    val datePickerDialog = android.app.DatePickerDialog(
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