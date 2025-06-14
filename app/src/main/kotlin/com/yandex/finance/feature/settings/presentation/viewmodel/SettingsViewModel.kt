package com.yandex.finance.feature.settings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.finance.feature.settings.domain.UiSettingsModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {

    private val _settingsUiState = MutableStateFlow(UiSettingsModel.initial)

    private val _uiState = MutableStateFlow<State>(State.Loading)

    val uiState: StateFlow<State> = _uiState.asStateFlow()

    init {
        loadData()
    }

    sealed interface State {

        data object Loading : State

        data object Error : State

        data class Content(val settingsUiState: StateFlow<UiSettingsModel>) : State
    }

    fun changeTheme(isDarkMode: Boolean) {
        _settingsUiState.value = _settingsUiState.value.copy(isDarkMode = isDarkMode)
    }

    private fun loadData() {
        viewModelScope.launch {
            delay(500)
            _uiState.value = State.Content(_settingsUiState.asStateFlow())
        }
    }
}
