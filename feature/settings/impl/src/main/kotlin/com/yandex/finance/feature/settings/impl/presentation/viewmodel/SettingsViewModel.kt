package com.yandex.finance.feature.settings.impl.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.finance.core.data.sync.SyncStatusRepository
import com.yandex.finance.core.datastore.SettingsDataStore
import com.yandex.finance.feature.settings.impl.domain.UiChapterModel
import com.yandex.finance.feature.settings.impl.domain.UiSettingsModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val syncStatusRepository: SyncStatusRepository,
    private val app: Application
) : AndroidViewModel(app) {

    private val _settingsUiState = MutableStateFlow(UiSettingsModel.initial)

    private val _uiState = MutableStateFlow<State>(State.Loading)

    val uiState: StateFlow<State> = _uiState.asStateFlow()

    private val chapters = listOf(
        UiChapterModel(title = "Основной цвет"),
        UiChapterModel(title = "Хаптики"),
        UiChapterModel(title = "Код пароль"),
        UiChapterModel(title = "Синхронизация"),
        UiChapterModel(title = "Язык"),
        UiChapterModel(title = "О программе"),
    )

    init {
        loadData()
        observeTheme()
    }

    sealed interface State {

        data object Loading : State

        @JvmInline
        value class Error(val retry: () -> Unit) : State

        data class Content(val settingsUiState: StateFlow<UiSettingsModel>) : State
    }

    private fun observeTheme() {
        viewModelScope.launch {
            SettingsDataStore.isDarkTheme(app.applicationContext).collect { isDark ->
                _settingsUiState.value = _settingsUiState.value.copy(isDarkMode = isDark)
            }
        }
    }

    fun changeTheme(isDarkMode: Boolean) {
        viewModelScope.launch {
            SettingsDataStore.setDarkTheme(app.applicationContext, isDarkMode)
        }
    }
    
    fun refreshSyncStatus() {
        viewModelScope.launch {
            try {
                val lastSyncTime = syncStatusRepository.getFormattedLastSyncTime()
                _settingsUiState.value = _settingsUiState.value.copy(
                    lastSyncTime = lastSyncTime
                )
            } catch (e: Exception) {
                Timber.e(e, "Failed to refresh sync status")
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            delay(500)

            try {
                val lastSyncTime = syncStatusRepository.getFormattedLastSyncTime()
                
                _settingsUiState.value = _settingsUiState.value.copy(
                    chapters = chapters,
                    lastSyncTime = lastSyncTime
                )
                _uiState.value = State.Content(_settingsUiState.asStateFlow())
            } catch (e: Exception) {
                Timber.e(e, "Failed to load sync status")
                _uiState.value = State.Error { loadData() }
            }
        }
    }
}
