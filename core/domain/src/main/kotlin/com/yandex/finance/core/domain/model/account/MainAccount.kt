package com.yandex.finance.core.domain.model.account

data class MainAccount(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val createdAt: String,
    val updatedAt: String,
    val incomeStats: List<IncomeStat>,
    val expenseStats: List<ExpenseStat>
)
