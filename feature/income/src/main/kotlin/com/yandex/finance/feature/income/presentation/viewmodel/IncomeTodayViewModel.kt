package com.yandex.finance.feature.income.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.finance.core.domain.repository.TransactionRepository
import com.yandex.finance.feature.income.domain.UiIncomeTodayModel
import com.yandex.finance.feature.income.domain.asUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date

class IncomeTodayViewModel(
    private val transactionRepository: TransactionRepository
) : ViewModel() {

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
        Timber.d("loadData was called")

        viewModelScope.launch(Dispatchers.Default) {
            _uiState.value = State.Loading

            val startDate = SimpleDateFormat("yyyy-MM-dd").format(Date()) // Будет изменено на kotlinx date time
            val endDate = SimpleDateFormat("yyyy-MM-dd").format(Date())

            transactionRepository.fetchTransactionsByPeriod(
                id = "1",
                startDate = startDate,
                endDate = endDate
            ).onSuccess {
                Timber.d("loadData was called with success: $it")

                var sum = 0
                val transactions = it.filter { transaction ->
                    transaction.category.isIncome
                }.asUiModel()
                transactions.forEach { transaction ->
                    sum += transaction.amount.substringBefore('.').toInt() // Пока так
                }

                _incomeTodayUiState.value = _incomeTodayUiState.value.copy(
                    sumOfAllIncomes = sum,
                    incomes = transactions
                )

                _uiState.value = State.Content(_incomeTodayUiState.asStateFlow())

            }.onFailure {
                Timber.e(it, "loadData was called with error: ")

                _uiState.value = State.Error
            }
        }
    }
}
