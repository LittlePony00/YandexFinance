package com.yandex.finance.core.domain.model.account

/**
 * Main account with statistics for income and expenses
 * 
 * @param id Unique account identifier
 * @param name Account display name
 * @param balance Current account balance
 * @param currency Account currency code
 * @param createdAt Account creation timestamp
 * @param updatedAt Last update timestamp
 * @param incomeStats List of income statistics by categories
 * @param expenseStats List of expense statistics by categories
 */
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
