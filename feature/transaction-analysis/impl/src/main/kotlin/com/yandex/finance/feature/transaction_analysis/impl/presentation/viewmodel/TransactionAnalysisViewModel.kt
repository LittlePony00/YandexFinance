package com.yandex.finance.feature.transaction_analysis.impl.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.finance.core.common.Id
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import com.yandex.finance.core.ui.util.dateTimeComponentsFormat
import com.yandex.finance.feature.transaction_analysis.api.domain.model.TransactionAnalysisModel
import com.yandex.finance.feature.transaction_analysis.api.domain.usecase.GetTransactionAnalysisUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.format
import timber.log.Timber


class TransactionAnalysisViewModel @AssistedInject constructor(
    private val id: Id,
    private val getTransactionAnalysisUseCase: GetTransactionAnalysisUseCase,
    @Assisted private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): TransactionAnalysisViewModel
    }

    private val isIncome: Boolean = savedStateHandle.get<Boolean>("isIncome") ?: false
    private val _analysisUiState = MutableStateFlow(TransactionAnalysisModel.initial.copy(isIncome = isIncome))

    private val _uiState = MutableStateFlow<State>(State.Loading)

    val uiState: StateFlow<State> = _uiState.asStateFlow()

    sealed interface State {

        data object Loading : State

        @JvmInline
        value class Error(val retry: () -> Unit) : State

        data class Content(
            val action: Action? = null,
            val analysisUiState: StateFlow<TransactionAnalysisModel>
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
        _analysisUiState.value = _analysisUiState.value.copy(startDate = date)
        loadData()
    }

    fun changeEndDate(endDate: Long?) {
        Timber.d("changeEndDate was called. endDate: $endDate")

        val date = endDate?.let {
            Instant.fromEpochMilliseconds(it).format(dateTimeComponentsFormat)
        } ?: Clock.System.now().format(dateTimeComponentsFormat)
        _analysisUiState.value = _analysisUiState.value.copy(endDate = date)
        loadData()
    }

    fun loadData() {
        Timber.d("loadData was called")

        viewModelScope.launch {
            _uiState.value = State.Loading

            getTransactionAnalysisUseCase(
                id = id,
                startDate = _analysisUiState.value.startDate,
                endDate = _analysisUiState.value.endDate,
                isIncome = _analysisUiState.value.isIncome
            ).onSuccess {
                Timber.d("getTransactionAnalysis was called with success: $it")

                _analysisUiState.value = it
                _uiState.value = State.Content(analysisUiState = _analysisUiState.asStateFlow())
            }.onFailure {
                Timber.e(it, "getTransactionAnalysis was called with error: ")

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