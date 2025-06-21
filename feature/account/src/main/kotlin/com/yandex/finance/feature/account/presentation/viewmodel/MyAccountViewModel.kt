package com.yandex.finance.feature.account.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.finance.core.domain.repository.AccountRepository
import com.yandex.finance.feature.account.domain.CurrencyType
import com.yandex.finance.feature.account.domain.UiAccountModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class MyAccountViewModel(
    private val accountRepository: AccountRepository
) : ViewModel() {

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
            accountRepository.fetchAccount("252").onSuccess {
                Timber.d("loadData was called with success: $it")

                _accountUiState.value = _accountUiState.value.copy(
                    id = it.id,
                    balance = it.balance,
                    icon = null,
                    currency = CurrencyType.convertFromString(it.currency)
                )
                _uiState.value = State.Content(_accountUiState.asStateFlow())
            }.onFailure {
                Timber.e(it, "loadData was called with error")
            }
        }
    }
}
