package com.yandex.finance.core.domain.repository

import com.yandex.finance.core.domain.model.category.Category

interface CategoryRepository {

    suspend fun fetchCategories(): Result<List<Category>>

    suspend fun fetchCategoriesByType(isIncome: Boolean): Result<List<Category>>
}
