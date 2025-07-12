package com.yandex.finance.feature.articles.domain.usecase

import com.yandex.finance.core.data.repository.CategoryRepository
import com.yandex.finance.core.domain.UseCase
import com.yandex.finance.core.domain.model.category.Category
import javax.inject.Inject

data class GetCategoriesUseCaseParams(
    val isIncome: Boolean? = null
)

class GetCategoriesUseCase @Inject constructor(
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
