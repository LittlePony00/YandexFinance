package com.yandex.finance.core.domain.model.account

/**
 * Expense statistics for a specific category
 * 
 * @param emoji Category emoji icon
 * @param amount Total expense amount for category
 * @param categoryId Category identifier
 * @param categoryName Category display name
 */
data class ExpenseStat(
    val emoji: String,
    val amount: String,
    val categoryId: Int,
    val categoryName: String
)
