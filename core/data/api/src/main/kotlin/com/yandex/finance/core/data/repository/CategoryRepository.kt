package com.yandex.finance.core.data.repository

import com.yandex.finance.core.domain.model.category.Category

/**
 * Interface for work with categories
 */
interface CategoryRepository {

    /**
     * Fetches all categories
     *
     * @return Result<List<[Category]>>
     */
    suspend fun fetchCategories(): Result<List<Category>>

    /**
     * Fetches categories by type
     *
     * @param isIncome true for income categories, false for expense categories
     *
     * @return Result<List<[Category]>>
     */
    suspend fun fetchCategoriesByType(isIncome: Boolean): Result<List<Category>>
}
