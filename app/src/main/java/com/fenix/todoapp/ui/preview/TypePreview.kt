package com.fenix.todoapp.ui.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.fenix.todoapp.ui.design.theme.Typography

@Preview
@Composable
fun TypePreview(){
    Column {
        Text(
            text = "Large title",
            style = Typography.titleLarge,
            )
        Text(
            text = "Title",
            style = Typography.titleMedium,
            )
        Text(
            text = "Button",
            style = Typography.bodyLarge,
            )
        Text(
            text = "Body",
            style = Typography.bodyMedium,
            )
        Text(
            text = "Subhead",
            style = Typography.titleSmall,
        )
    }
}
