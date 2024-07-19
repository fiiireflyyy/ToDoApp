package com.fenix.todoapp.ui.settingsThemeScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fenix.todoapp.domain.model.SettingsTheme
import com.fenix.todoapp.ui.settingsThemeScreen.SettingsThemeViewModel


@Composable
fun SettingsThemeScreen(
    viewModel: SettingsThemeViewModel,
) {
    val userThemeChoice = viewModel.userThemeChoice.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                horizontal = 8.dp,
                vertical = 16.dp,
            ),
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = viewModel::navigateBack,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }

            Box(modifier = Modifier.weight(1f)) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Выберите тему",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }


        UserThemeChoiceSelector(
            currentUserThemeChoice = SettingsTheme.LightThemeChoice,
            selectedUserThemeChoice = userThemeChoice,
            onChooseItem = viewModel::chooseUserTheme,
        )

        UserThemeChoiceSelector(
            currentUserThemeChoice = SettingsTheme.DarkThemeChoice,
            selectedUserThemeChoice = userThemeChoice,
            onChooseItem = viewModel::chooseUserTheme,
        )

        UserThemeChoiceSelector(
            currentUserThemeChoice = SettingsTheme.SystemThemeChoice,
            selectedUserThemeChoice = userThemeChoice,
            onChooseItem = viewModel::chooseUserTheme,
        )

    }
}