package com.yandex.finance.feature.income.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.finance.core.common.Id
import com.yandex.finance.feature.income.domain.GetUiIncomeTodayModelByPeriodUseCase
import com.yandex.finance.feature.income.domain.UiIncomeMainModel
import com.yandex.finance.core.ui.util.dateTimeComponentsFormat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.format
import timber.log.Timber

class IncomeTodayViewModel(
    private val id: Id,
    private val getUiIncomeTodayModelByPeriodUseCase: GetUiIncomeTodayModelByPeriodUseCase
) : ViewModel() {

    private val _incomeTodayUiState = MutableStateFlow(UiIncomeMainModel.initial)

    private val _uiState = MutableStateFlow<State>(State.Loading)

    val uiState: StateFlow<State> = _uiState.asStateFlow()

    init {
        loadData()
    }

    sealed interface State {

        data object Loading : State

        @JvmInline
        value class Error(val retry: () -> Unit) : State

        data class Content(val incomeTodayUiState: StateFlow<UiIncomeMainModel>) : State
    }

    private fun loadData() {
        Timber.d("loadData was called")

        viewModelScope.launch {
            _uiState.value = State.Loading

            val startDate = Clock.System.now().format(dateTimeComponentsFormat)
            val endDate = Clock.System.now().format(dateTimeComponentsFormat)

            getUiIncomeTodayModelByPeriodUseCase(
                id = id,
                startDate = startDate,
                endDate = endDate
            ).onSuccess {
                Timber.d("getUiIncomeTodayModelByPeriodUseCase was called with success: $it")

                _incomeTodayUiState.value = it
                _uiState.value = State.Content(_incomeTodayUiState.asStateFlow())
            }.onFailure {
                Timber.e(it, "getUiIncomeTodayModelByPeriodUseCase was called with error: ")

                _uiState.value = State.Error(retry = { loadData() })
            }
        }
    }
}
