package com.yandex.finance.feature.articles.domain.usecase

import androidx.compose.runtime.snapshotFlow
import com.yandex.finance.core.data.repository.CategoryRepository
import com.yandex.finance.core.domain.UseCase
import com.yandex.finance.core.domain.model.category.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

data class GetCategoriesUseCaseParams(
    val isIncome: Boolean? = null
)

class GetCategoriesUseCase(
    private val categoryRepository: CategoryRepository
) : UseCase {

    suspend operator fun invoke(params: GetCategoriesUseCaseParams): Result<List<Category>> {
        val result = if (params.isIncome != null) {
            categoryRepository.fetchCategoriesByType(params.isIncome)
        } else {
            categoryRepository.fetchCategories()
        }

        return result
    }
}
