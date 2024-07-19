package com.fenix.todoapp.ui.settingsThemeScreen.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fenix.todoapp.domain.model.SettingsTheme
import com.fenix.todoapp.ui.design.theme.blue


@Composable
fun UserThemeChoiceSelector(
    currentUserThemeChoice: SettingsTheme,
    selectedUserThemeChoice: SettingsTheme,
    onChooseItem: (SettingsTheme) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = function(selectedUserThemeChoice, currentUserThemeChoice, onChooseItem),
    )
}

@Composable
private fun function(
    selectedUserThemeChoice: SettingsTheme,
    currentUserThemeChoice: SettingsTheme,
    onChooseItem: (SettingsTheme) -> Unit
): @Composable() (RowScope.() -> Unit) =
    {
        RadioButton(
            colors = RadioButtonColors(
                selectedColor = MaterialTheme.colorScheme.blue,
                unselectedColor = MaterialTheme.colorScheme.outline,
                disabledSelectedColor = MaterialTheme.colorScheme.outline,
                disabledUnselectedColor = MaterialTheme.colorScheme.outline,
            ),
            selected = selectedUserThemeChoice == currentUserThemeChoice,
            onClick = { onChooseItem(currentUserThemeChoice) },
        )

        Text(
            text = when (currentUserThemeChoice) {
                is SettingsTheme.DarkThemeChoice -> "Всегда темная"
                is SettingsTheme.LightThemeChoice -> "Всегда светлая"
                is SettingsTheme.SystemThemeChoice -> "Как в системе"
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }