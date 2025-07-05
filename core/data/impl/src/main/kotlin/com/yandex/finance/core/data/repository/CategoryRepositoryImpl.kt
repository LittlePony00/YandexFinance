package com.yandex.finance.core.data.repository

import com.yandex.finance.core.data.mapper.asExternalModel
import com.yandex.finance.core.data.mapper.asAnotherResult
import com.yandex.finance.core.domain.model.category.Category
import com.yandex.finance.core.network.category.service.CategoryService

class CategoryRepositoryImpl(
    private val categoryService: CategoryService
) : CategoryRepository {

    override suspend fun fetchCategories(): Result<List<Category>> {
        return categoryService.fetchCategories().asAnotherResult { categoryList ->
            categoryList.map { it.asExternalModel() }
        }
    }

    override suspend fun fetchCategoriesByType(isIncome: Boolean): Result<List<Category>> {
        return categoryService.fetchCategoriesByType(isIncome).asAnotherResult { categoryList ->
            categoryList.map { it.asExternalModel() }
        }
    }
}
