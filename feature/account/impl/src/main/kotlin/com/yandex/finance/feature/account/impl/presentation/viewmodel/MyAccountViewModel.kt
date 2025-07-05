package com.yandex.finance.feature.account.impl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.finance.core.common.Id
import com.yandex.finance.core.data.repository.AccountRepository
import com.yandex.finance.core.domain.model.CurrencyType
import com.yandex.finance.feature.account.impl.domain.UiAccountModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class MyAccountViewModel(
    private val id: Id,
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _accountUiState = MutableStateFlow(UiAccountModel.initial)
    val accountUiState = _accountUiState.asStateFlow()

    private val _uiState = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    init {
        loadData()
    }

    sealed interface State {

        data object Loading : State

        @JvmInline
        value class Error(val retry: () -> Unit) : State

        data object Content : State
    }


    private fun loadData() {
        Timber.d("loadData was called")

        viewModelScope.launch {
            accountRepository.fetchAccount(id).onSuccess {
                Timber.d("loadData was called with success: $it")

                _accountUiState.value = _accountUiState.value.copy(
                    id = it.id,
                    name = it.name,
                    balance = it.balance,
                    icon = "\uD83D\uDCB0",
                    currency = CurrencyType.convertFromString(it.currency)
                )
                _uiState.value = State.Content
            }.onFailure {
                Timber.e(it, "loadData was called with error")

                _uiState.value = State.Error(retry = { loadData() })
            }
        }
    }

    override fun onCleared() {
        Timber.d("onCleared was called")
        super.onCleared()
    }
}
