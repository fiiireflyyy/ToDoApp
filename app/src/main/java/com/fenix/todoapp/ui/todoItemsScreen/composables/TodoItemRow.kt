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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
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
            .semantics(mergeDescendants = true) {
                liveRegion = LiveRegionMode.Polite
                contentDescription = item.description
                stateDescription = if (item.isDone) {
                    "Выполнено"
                } else {
                    "Не выполнено"
                }
            }
    ) {
        Checkbox(
            modifier = Modifier
                .padding(start = 4.dp)
                .clip(RoundedCornerShape(2.dp)),
            checked = item.isDone,
            onCheckedChange = { onCheckedChange(item.id, it) },
            colors = CheckboxColors(
                checkedCheckmarkColor = MaterialTheme.colorScheme.backSecond,
                checkedBoxColor = MaterialTheme.colorScheme.green,
                checkedBorderColor = MaterialTheme.colorScheme.green,
                uncheckedCheckmarkColor = MaterialTheme.colorScheme.white,
                uncheckedBorderColor = if (item.importance is Importance.High) {
                    MaterialTheme.colorScheme.red
                } else {
                    MaterialTheme.colorScheme.outline
                },
                uncheckedBoxColor = if (item.importance is Importance.High) {
                    MaterialTheme.colorScheme.lightRed
                } else {
                    MaterialTheme.colorScheme.backSecond
                },
                disabledBorderColor = MaterialTheme.colorScheme.white,
                disabledIndeterminateBorderColor = MaterialTheme.colorScheme.white,
                disabledCheckedBoxColor = MaterialTheme.colorScheme.white,
                disabledUncheckedBoxColor = MaterialTheme.colorScheme.white,
                disabledIndeterminateBoxColor = MaterialTheme.colorScheme.white,
                disabledUncheckedBorderColor = MaterialTheme.colorScheme.white,
            )
        )
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(vertical = 12.dp)
                .clearAndSetSemantics { },
        ) {
            if (item.importance is Importance.High) {
                Icon(
                    modifier = Modifier.padding(top = 2.dp, end = 4.dp),
                    painter = painterResource(id = R.drawable.hight_importance),
                    contentDescription = stringResource(id = R.string.urgent_icon_description),
                    tint = MaterialTheme.colorScheme.red,
                )
            }
            if (item.importance is Importance.Low) {
                Icon(
                    modifier = Modifier.padding(top = 2.dp),
                    painter = painterResource(id = R.drawable.low_importance),
                    contentDescription = stringResource(id = R.string.low_icon_description),
                    tint = MaterialTheme.colorScheme.gray
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .clearAndSetSemantics { }
            ) {
                Text(
                    modifier = Modifier.clickable { onClick(item.id) },
                    color = MaterialTheme.colorScheme.label,
                    style = if (item.isDone) MaterialTheme.typography.bodySmall else MaterialTheme.typography.bodyMedium,
                    text = item.description,
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
                if (item.deadline != null) {
                    Text(
                        text = item.deadline,
                        style = MaterialTheme.typography.headlineMedium,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.tertiry
                    )
                }
            }
        }
        IconButton(
            onClick = { onClick(item.id) },
            modifier = Modifier
                .padding(
                    end = 16.dp,
                    bottom = 12.dp,
                    start = 12.dp,
                )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_info),
                contentDescription = stringResource(id = R.string.info_item_icon_description),
                tint = MaterialTheme.colorScheme.tertiry,
            )
        }
    }
}
