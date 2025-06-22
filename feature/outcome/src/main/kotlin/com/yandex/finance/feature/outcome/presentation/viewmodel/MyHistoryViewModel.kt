package com.yandex.finance.feature.outcome.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.finance.core.common.Id
import com.yandex.finance.core.ui.util.dateTimeComponentsFormat
import com.yandex.finance.feature.outcome.domain.GetUiOutcomeTodayModelByPeriodUseCase
import com.yandex.finance.feature.outcome.domain.UiMyHistoryModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.format
import timber.log.Timber

class MyHistoryViewModel(
    private val id: Id,
    private val getUiOutcomeTodayModelByPeriodUseCase: GetUiOutcomeTodayModelByPeriodUseCase,
) : ViewModel() {

    private val _myHistoryUiState = MutableStateFlow(UiMyHistoryModel.initial)

    private val _uiState = MutableStateFlow<State>(State.Loading)

    val uiState: StateFlow<State> = _uiState.asStateFlow()

    init {
        loadData()
    }

    sealed interface State {

        data object Loading : State

        @JvmInline
        value class Error(val retry: () -> Unit) : State

        data class Content(
            val action: Action? = null,
            val myHistoryUiState: StateFlow<UiMyHistoryModel>
        ) : State
    }

    sealed interface Action {

        data object ChangeStartDate : Action

        data object ChangeEndDate : Action
    }

    fun changeAction(action: Action? = null) {
        onContentDisplayState { content ->
            Timber.d("change action was called. action: $action")

            _uiState.value = content.copy(action = action)
        }
    }

    fun changeStartDate(startDate: Long?) {
        Timber.d("changeStartDate was called. startDate: $startDate")

        val date = startDate?.let {
            Instant.fromEpochMilliseconds(it).format(dateTimeComponentsFormat)
        } ?: Clock.System.now().format(dateTimeComponentsFormat)
        _myHistoryUiState.value = _myHistoryUiState.value.copy(startDate = date)
        loadData()
    }

    fun changeEndDate(endDate: Long?) {
        Timber.d("changeStartDate was called. endDate: $endDate")

        val date = endDate?.let {
            Instant.fromEpochMilliseconds(it).format(dateTimeComponentsFormat)
        } ?: Clock.System.now().format(dateTimeComponentsFormat)
        _myHistoryUiState.value =
            _myHistoryUiState.value.copy(endDate = date)
        loadData()
    }

    private fun loadData() {
        Timber.d("loadData was called")

        viewModelScope.launch {
            _uiState.value = State.Loading

            getUiOutcomeTodayModelByPeriodUseCase(
                id = id,
                startDate = _myHistoryUiState.value.startDate,
                endDate = _myHistoryUiState.value.endDate
            ).onSuccess {
                Timber.d("getUiIncomeTodayModelByPeriodUseCase was called with success: $it")

                _myHistoryUiState.value = _myHistoryUiState.value.copy(
                    uiIncomeMainModel = it
                )
                _uiState.value = State.Content(myHistoryUiState = _myHistoryUiState.asStateFlow())
            }.onFailure {
                Timber.e(it, "getUiIncomeTodayModelByPeriodUseCase was called with error: ")

                _uiState.value = State.Error(retry = { loadData() })
            }
        }
    }

    private inline fun onContentDisplayState(block: (content: State.Content) -> Unit) {
        val state = _uiState.value
        if (state is State.Content) {
            block(state)
        }
    }
}
