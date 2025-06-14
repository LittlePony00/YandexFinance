package com.yandex.finance.core.domain

data class Transaction(
    val id: Int,
    val account: Account,
    val category: Category,
    val amount: String,
    val transactionDate: String,
    val comment: String?,
    val createdAt: String,
    val updatedAt: String,
)
