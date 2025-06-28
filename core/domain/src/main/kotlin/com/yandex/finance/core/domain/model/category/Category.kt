package com.yandex.finance.core.domain.model.category

/**
 * Transaction category information
 * 
 * @param id Unique category identifier
 * @param name Category display name
 * @param emoji Optional category emoji icon
 * @param isIncome Whether this category is for income transactions
 */
data class Category(
    val id: Int,
    val name: String,
    val emoji: String? = null,
    val isIncome: Boolean,
)
