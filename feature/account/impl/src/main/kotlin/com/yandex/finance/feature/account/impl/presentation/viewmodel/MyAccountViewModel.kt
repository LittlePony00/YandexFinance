package com.yandex.finance.feature.account.impl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.finance.core.common.Id
import com.yandex.finance.core.data.repository.AccountRepository
import com.yandex.finance.core.domain.model.CurrencyType
import com.yandex.finance.feature.account.impl.domain.UiAccountModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class BalanceChartItem(
    val balance: Double,
    val change: Double,
    val date: String,
    val isIncrease: Boolean = change > 0
)

class MyAccountViewModel @Inject constructor(
    private val id: Id,
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _accountUiState = MutableStateFlow(UiAccountModel.initial)
    val accountUiState = _accountUiState.asStateFlow()

    private val _uiState = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    private val _balanceChartData = MutableStateFlow<List<BalanceChartItem>>(emptyList())
    val balanceChartData = _balanceChartData.asStateFlow()

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

        viewModelScope.launch(Dispatchers.Default) {
            accountRepository.fetchAccount(id).onSuccess {
                Timber.d("loadData was called with success: $it")

                _accountUiState.value = _accountUiState.value.copy(
                    id = it.id,
                    name = it.name,
                    balance = it.balance,
                    icon = "\uD83D\uDCB0",
                    currency = CurrencyType.convertFromString(it.currency)
                )

                accountRepository.fetchAccountHistory(id).onSuccess { accountHistory ->
                    val balanceHistory =
                        accountHistory.history.map { s ->
                            s.newState?.balance
                                ?.toDoubleOrNull()
                                ?.toInt() ?: 0
                        }

                    _accountUiState.value = _accountUiState.value.copy(
                        balanceHistory = balanceHistory
                    )

                    Timber.d("${accountHistory.history.size}")
                    val chartData = accountHistory.history
                        .mapNotNull { history ->
                            val newBalance = history.newState?.balance?.toDoubleOrNull() ?: return@mapNotNull null
                            val previousBalance = history.previousState?.balance?.toDoubleOrNull() ?: return@mapNotNull null
                            
                            val change = newBalance - previousBalance
                            BalanceChartItem(
                                balance = newBalance,
                                change = change,
                                date = history.createdAt,
                                isIncrease = newBalance > previousBalance
                            )
                        }
                        .mapIndexed { index, item ->
                            item.copy(date = "${item.date}_$index")
                        }

                    _balanceChartData.value = chartData
                }.onFailure { error ->
                    Timber.e(error, "ERROR: ")
                }

                _uiState.value = State.Content
            }.onFailure { error ->
                Timber.e(error, "ERROR: ")
                _uiState.value = State.Error(retry = { loadData() })
            }
        }
    }

    override fun onCleared() {
        Timber.d("onCleared was called")
        super.onCleared()
    }
}
