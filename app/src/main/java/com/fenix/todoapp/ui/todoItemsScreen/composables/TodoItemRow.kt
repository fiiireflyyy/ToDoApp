package com.fenix.todoapp.ui.todoItemsScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.fenix.todoapp.ui.design.theme.backSecond
import com.fenix.todoapp.ui.design.theme.gray
import com.fenix.todoapp.ui.design.theme.green
import com.fenix.todoapp.ui.design.theme.label
import com.fenix.todoapp.ui.design.theme.lightRed
import com.fenix.todoapp.ui.design.theme.red
import com.fenix.todoapp.ui.design.theme.tertiry
import com.fenix.todoapp.ui.design.theme.white
import com.fenix.todoapp.ui.todoItemsScreen.state.TodoItemModelUi
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TodoItemRow(
    item: TodoItemModelUi,
    onCheckedChange: (String, Boolean) -> Unit,
    onClick: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.backSecond)
    ){
        Checkbox(
            modifier = Modifier
                .padding(start = 4.dp)
                .clip(RoundedCornerShape(2.dp)),
            checked = item.isDone,
            onCheckedChange = {onCheckedChange(item.id, it)},
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
