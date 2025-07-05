package com.yandex.finance.core.network.category.service

import com.yandex.finance.core.network.category.model.NetworkCategory

interface CategoryService {

    suspend fun fetchCategories(): Result<List<NetworkCategory>>

    suspend fun fetchCategoriesByType(isIncome: Boolean): Result<List<NetworkCategory>>
}
