package com.yandex.finance.feature.outcome.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.finance.core.common.Id
import com.yandex.finance.core.ui.util.dateTimeComponentsFormat
import com.yandex.finance.feature.outcome.domain.GetUiOutcomeTodayModelByPeriodUseCase
import com.yandex.finance.feature.outcome.domain.UiOutcomeMainModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.format
import timber.log.Timber

class OutcomeTodayViewModel(
    private val id: Id,
    private val getUiOutcomeTodayModelByPeriodUseCase: GetUiOutcomeTodayModelByPeriodUseCase,
) : ViewModel() {

    private val _outcomeTodayUiState = MutableStateFlow(UiOutcomeMainModel.initial)

    private val _uiState = MutableStateFlow<State>(State.Loading)

    val uiState: StateFlow<State> = _uiState.asStateFlow()

    init {
        loadData()
    }

    sealed interface State {

        data object Loading : State

        @JvmInline
        value class Error(val retry: () -> Unit) : State

        data class Content(val outcomeTodayUiState: StateFlow<UiOutcomeMainModel>) : State
    }

    private fun loadData() {
        Timber.d("loadData was called")

        viewModelScope.launch {
            _uiState.value = State.Loading

            val startDate = Clock.System.now().format(dateTimeComponentsFormat)
            val endDate = Clock.System.now().format(dateTimeComponentsFormat)

            getUiOutcomeTodayModelByPeriodUseCase(
                id = id,
                startDate = startDate,
                endDate = endDate
            ).onSuccess {
                Timber.d("getUiOutcomeTodayModelByPeriodUseCase was called with success: $it")

                _outcomeTodayUiState.value = it
                _uiState.value = State.Content(_outcomeTodayUiState.asStateFlow())
            }.onFailure {
                Timber.e(it, "getUiOutcomeTodayModelByPeriodUseCase was called with error: ")

                _uiState.value = State.Error(retry = { loadData() })
            }
        }
    }
}
