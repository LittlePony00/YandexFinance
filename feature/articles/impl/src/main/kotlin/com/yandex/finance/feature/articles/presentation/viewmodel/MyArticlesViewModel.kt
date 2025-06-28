package com.yandex.finance.feature.articles.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.finance.feature.articles.domain.UiArticleModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyArticlesViewModel : ViewModel() {

    private val _myArticlesUiState = MutableStateFlow(UiArticleModel.initial)

    private val _uiState = MutableStateFlow<State>(State.Loading)

    val uiState: StateFlow<State> = _uiState.asStateFlow()

    init {
        loadData()
    }

    sealed interface State {

        data object Loading : State

        data object Error : State

        data class Content(val myArticlesUiState: StateFlow<UiArticleModel>) : State
    }

    private fun loadData() {
        viewModelScope.launch {
            delay(500)
            _uiState.value = State.Content(_myArticlesUiState.asStateFlow())
        }
    }
}
