package com.fenix.todoapp.ui.addTodoScreen.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fenix.todoapp.R
import com.fenix.todoapp.ui.design.theme.backSecond
import com.fenix.todoapp.ui.design.theme.tertiry
import com.fenix.todoapp.ui.design.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomCard(
    paddingValues: PaddingValues,
    description: String,
    setDescription: (String) -> Unit,
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
            onValueChange = { setDescription(it) },
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
}