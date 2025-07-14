package com.yandex.finance.outcome.impl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.finance.core.common.Id
import com.yandex.finance.core.ui.util.dateTimeComponentsFormat
import com.yandex.finance.feature.outcome.api.domain.model.UiMyHistoryModel
import com.yandex.finance.feature.outcome.api.domain.usecase.GetUiTransactionByPeriodUseCase
import com.yandex.finance.outcome.impl.domain.model.initialOutcome
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.format
import timber.log.Timber
import javax.inject.Inject

class MyHistoryOutcomeViewModel @Inject constructor(
    private val id: Id,
    private val getUiTransactionByPeriodUseCase: GetUiTransactionByPeriodUseCase,
) : ViewModel() {

    private val _myHistoryUiState = MutableStateFlow(UiMyHistoryModel.initialOutcome)

    private val _uiState = MutableStateFlow<State>(State.Loading)

    val uiState: StateFlow<State> = _uiState.asStateFlow()

    sealed interface State {

        data object Loading : State

        @JvmInline
        value class Error(val retry: () -> Unit) : State

        data class Content(
            val action: ContentAction? = null,
            val myHistoryUiState: StateFlow<UiMyHistoryModel>
        ) : State
    }

    sealed interface ContentAction {

        data object ChangeStartDate : ContentAction

        data object ChangeEndDate : ContentAction
    }

    fun changeAction(action: ContentAction? = null) {
        Timber.d("change action was called. action: $action")

        onContentDisplayState { content ->
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
        Timber.d("changeEndDate was called. endDate: $endDate")

        val date = endDate?.let {
            Instant.fromEpochMilliseconds(it).format(dateTimeComponentsFormat)
        } ?: Clock.System.now().format(dateTimeComponentsFormat)
        _myHistoryUiState.value =
            _myHistoryUiState.value.copy(endDate = date)

        loadData()
    }

    fun loadData() {
        Timber.d("loadData was called")

        viewModelScope.launch {
            _uiState.value = State.Loading

            getUiTransactionByPeriodUseCase(
                id = id,
                isIncome = false,
                endDate = _myHistoryUiState.value.endDate,
                startDate = _myHistoryUiState.value.startDate
            ).onSuccess {
                Timber.d("getUiTransactionByPeriod was called with success: $it")

                _myHistoryUiState.value = _myHistoryUiState.value.copy(
                    uiTransactionMainModel = it
                )
                _uiState.value = State.Content(myHistoryUiState = _myHistoryUiState.asStateFlow())
            }.onFailure {
                Timber.e(it, "getUiTransactionByPeriod was called with error: ")

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
