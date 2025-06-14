package com.yandex.finance.feature.account.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.finance.feature.account.domain.UiAccountModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyAccountViewModel : ViewModel() {

    private val _accountUiState = MutableStateFlow(UiAccountModel.initial)

    private val _uiState = MutableStateFlow<State>(State.Loading)

    val uiState: StateFlow<State> = _uiState.asStateFlow()

    init {
        loadData()
    }

    sealed interface State {

        data object Loading : State

        data object Error : State

        data class Content(val accountUiState: StateFlow<UiAccountModel>) : State
    }


    private fun loadData() {
        viewModelScope.launch {
            delay(500)
            _uiState.value = State.Content(_accountUiState.asStateFlow())
        }
    }
}