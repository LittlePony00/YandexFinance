package com.yandex.finance.feature.articles.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.finance.core.domain.model.category.Category
import com.yandex.finance.feature.articles.domain.UiArticleModel
import com.yandex.finance.feature.articles.domain.toUiArticleModel
import com.yandex.finance.feature.articles.domain.usecase.GetCategoriesUseCase
import com.yandex.finance.feature.articles.domain.usecase.GetCategoriesUseCaseParams
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyArticlesViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isIncomeFilter = MutableStateFlow<Boolean?>(null)
    val isIncomeFilter: StateFlow<Boolean?> = _isIncomeFilter.asStateFlow()

    private val _articles = MutableStateFlow<List<UiArticleModel>>(emptyList())
    val articles: StateFlow<List<UiArticleModel>> = _articles.asStateFlow()

    private var allCategories: List<Category> = emptyList()

    init {
        loadInitialData()
        setupSearchFlow()
        _uiState.value = State.Content(articles)
    }

    sealed interface State {
        data object Loading : State
        data object Error : State
        data class Content(val articles: StateFlow<List<UiArticleModel>>) : State
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onFilterChanged(isIncome: Boolean?) {
        _isIncomeFilter.value = isIncome
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            getCategoriesUseCase(GetCategoriesUseCaseParams()).fold(
                onSuccess = { categories ->
                    allCategories = categories
                    applyFilters()
                },
                onFailure = {
                    _uiState.value = State.Error
                }
            )
        }
    }

    @OptIn(FlowPreview::class)
    private fun setupSearchFlow() {
        viewModelScope.launch {
            combine(
                _searchQuery.debounce(500).distinctUntilChanged(),
                _isIncomeFilter
            ) { _, _ ->
            }.collectLatest {
                applyFilters()
            }
        }
    }

    private fun applyFilters() {
        val searchQuery = _searchQuery.value
        val isIncomeFilter = _isIncomeFilter.value
        
        val filteredCategories = allCategories
            .filter { category ->
                isIncomeFilter == null || category.isIncome == isIncomeFilter
            }
            .filter { category ->
                searchQuery.isBlank() || category.name.contains(searchQuery, ignoreCase = true)
            }
        
        _articles.value = filteredCategories.map { it.toUiArticleModel() }
    }
}
