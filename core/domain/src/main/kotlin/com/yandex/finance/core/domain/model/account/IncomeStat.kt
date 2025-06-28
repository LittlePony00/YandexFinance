package com.yandex.finance.core.domain.model.account

/**
 * Income statistics for a specific category
 * 
 * @param emoji Category emoji icon
 * @param amount Total income amount for category
 * @param categoryId Category identifier
 * @param categoryName Category display name
 */
data class IncomeStat(
    val emoji: String,
    val amount: String,
    val categoryId: Int,
    val categoryName: String
)
