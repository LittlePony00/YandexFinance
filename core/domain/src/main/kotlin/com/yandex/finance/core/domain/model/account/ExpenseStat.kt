package com.yandex.finance.core.domain.model.account

data class ExpenseStat(
    val emoji: String,
    val amount: String,
    val categoryId: Int,
    val categoryName: String
)
