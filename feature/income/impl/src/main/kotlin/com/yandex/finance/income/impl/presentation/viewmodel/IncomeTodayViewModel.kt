package com.yandex.finance.income.impl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.finance.core.common.Id
import com.yandex.finance.core.ui.util.dateTimeComponentsFormat
import com.yandex.finance.feature.outcome.api.domain.model.UiTransactionMainModel
import com.yandex.finance.feature.outcome.api.domain.usecase.GetUiTransactionByPeriodUseCase
import com.yandex.finance.income.impl.domain.model.initialIncome
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.format
import timber.log.Timber
import javax.inject.Inject

class IncomeTodayViewModel @Inject constructor(
    private val id: Id,
    private val getUiTransactionByPeriodUseCase: GetUiTransactionByPeriodUseCase
) : ViewModel() {

    private val _incomeTodayUiState = MutableStateFlow(UiTransactionMainModel.Companion.initialIncome)

    private val _uiState = MutableStateFlow<State>(State.Loading)

    val uiState: StateFlow<State> = _uiState.asStateFlow()

    init {
        loadData()
    }

    sealed interface State {

        data object Loading : State

        @JvmInline
        value class Error(val retry: () -> Unit) : State

        data class Content(val incomeTodayUiState: StateFlow<UiTransactionMainModel>) : State
    }

    private fun loadData() {
        Timber.d("loadData was called")

        viewModelScope.launch {
            _uiState.value = State.Loading

            val startDate = Clock.System.now().format(dateTimeComponentsFormat)
            val endDate = Clock.System.now().format(dateTimeComponentsFormat)

            getUiTransactionByPeriodUseCase(
                id = id,
                startDate = startDate,
                endDate = endDate,
                isIncome = true
            ).onSuccess {
                Timber.d("getUiTransactionByPeriod was called with success: $it")

                _incomeTodayUiState.value = it
                _uiState.value = State.Content(_incomeTodayUiState.asStateFlow())
            }.onFailure {
                Timber.e(it, "getUiTransactionByPeriod was called with error: ")

                _uiState.value = State.Error(retry = { loadData() })
            }
        }
    }
}
