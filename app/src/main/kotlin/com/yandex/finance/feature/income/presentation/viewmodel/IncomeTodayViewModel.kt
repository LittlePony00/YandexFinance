package com.yandex.finance.feature.income.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.finance.feature.income.domain.UiIncomeTodayModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class IncomeTodayViewModel : ViewModel() {

    private val _incomeTodayUiState = MutableStateFlow(UiIncomeTodayModel.initial)

    private val _uiState = MutableStateFlow<State>(State.Loading)

    val uiState: StateFlow<State> = _uiState.asStateFlow()

    init {
        loadData()
    }

    sealed interface State {

        data object Loading : State

        data object Error : State

        data class Content(val incomeTodayUiState: StateFlow<UiIncomeTodayModel>) : State
    }

    private fun loadData() {
        viewModelScope.launch {
            delay(500)
            _uiState.value = State.Content(_incomeTodayUiState.asStateFlow())
        }
    }
}
