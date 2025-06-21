package com.yandex.finance.core.data.repository

import com.yandex.finance.core.data.mapper.asExternalModel
import com.yandex.finance.core.data.mapper.asExternalResult
import com.yandex.finance.core.domain.model.category.Category
import com.yandex.finance.core.domain.repository.CategoryRepository
import com.yandex.finance.core.network.category.service.CategoryService

class CategoryRepositoryImpl(
    private val categoryService: CategoryService
) : CategoryRepository {

    override suspend fun fetchCategories(): Result<List<Category>> {
        return categoryService.fetchCategories().asExternalResult { categoryList ->
            categoryList.map { it.asExternalModel() }
        }
    }

    override suspend fun fetchCategoriesByType(isIncome: Boolean): Result<List<Category>> {
        return categoryService.fetchCategoriesByType(isIncome).asExternalResult { categoryList ->
            categoryList.map { it.asExternalModel() }
        }
    }
}
