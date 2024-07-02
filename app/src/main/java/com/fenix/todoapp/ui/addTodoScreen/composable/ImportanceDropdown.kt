package com.fenix.todoapp.ui.addTodoScreen.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fenix.todoapp.R
import com.fenix.todoapp.domain.model.Importance
import com.fenix.todoapp.ui.design.theme.blue
import com.fenix.todoapp.ui.design.theme.gray
import com.fenix.todoapp.ui.design.theme.label
import com.fenix.todoapp.ui.design.theme.red

@Composable
fun ImportanceDropdown(importance: Importance, onImportanceChange: (Importance) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        TextButton(
            onClick = { expanded = true }) {
            Row {
                if (importance == Importance.High) {
                    Icon(
                        painter = painterResource(id = R.drawable.hight_importance),
                        contentDescription = "Warning Icon",
                        tint = MaterialTheme.colorScheme.red,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }
                if(importance == Importance.Low){
                    Icon(
                        painter = painterResource(id = R.drawable.low_importance),
                        contentDescription = "Low Priority",
                        tint = MaterialTheme.colorScheme.gray,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }
                Text(
                    text = when (importance) {
                        Importance.Low -> "низкая"
                        Importance.Medium -> "обычная"
                        Importance.High -> "срочная"
                    },
                    color = if (importance == Importance.High) MaterialTheme.colorScheme.red else MaterialTheme.colorScheme.blue, // Устанавливаем красный цвет только для срочной важности
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_medium))
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.low_importance),
                            contentDescription = "Low Priority Icon",
                            tint = Color.Gray,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        Text(
                            text = "низкая",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            color = MaterialTheme.colorScheme.label,
                        )
                    }
                },
                onClick = {
                    onImportanceChange(Importance.Low)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "обычная",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        color = MaterialTheme.colorScheme.label,
                    )
                },
                onClick = {
                    onImportanceChange(Importance.Medium)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.hight_importance),
                            contentDescription = "Warning Icon",
                            tint = MaterialTheme.colorScheme.red,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        Text(
                            text = "срочная",
                            color = MaterialTheme.colorScheme.red,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        )
                    }
                },
                onClick = {
                    onImportanceChange(Importance.High)
                    expanded = false
                }
            )
        }
    }
}
