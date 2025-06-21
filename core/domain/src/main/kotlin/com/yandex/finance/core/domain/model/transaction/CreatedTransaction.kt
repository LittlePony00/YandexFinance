package com.yandex.finance.core.domain.model.transaction

data class CreatedTransaction(
    val id: Int,
    val amount: String,
    val accountId: Int,
    val categoryId: Int,
    val comment: String,
    val updatedAt: String,
    val createdAt: String,
    val transactionDate: String
)
