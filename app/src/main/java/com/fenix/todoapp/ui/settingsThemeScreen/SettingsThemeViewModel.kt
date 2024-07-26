package com.fenix.todoapp.ui.settingsThemeScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fenix.todoapp.data.preferences.PreferencesManager
import com.fenix.todoapp.domain.model.SettingsTheme
import com.fenix.todoapp.navigation.NavManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsThemeViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val navManager: NavManager,
): ViewModel() {

    private val _userThemeChoice = MutableStateFlow<SettingsTheme>(SettingsTheme.SystemThemeChoice)
    val userThemeChoice = _userThemeChoice.asStateFlow()

    init {
        collectUserThemeChoice()
    }

    fun chooseUserTheme(userThemeChoice: SettingsTheme){
        viewModelScope.launch(Dispatchers.Default) {
            preferencesManager.saveSelectedUserThemeChoice(userThemeChoice)
        }
    }

    fun navigateBack(){
        navManager.navigateBack()
    }

    private fun collectUserThemeChoice(){
        viewModelScope.launch {
            preferencesManager.selectedUserThemeChoice.collect{
                _userThemeChoice.value = it
            }
        }
    }
}