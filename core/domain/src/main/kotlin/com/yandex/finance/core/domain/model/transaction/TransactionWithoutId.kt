package com.yandex.finance.core.domain.model.transaction

data class TransactionWithoutId(
    val accountId: Int,
    val amount: String,
    val categoryId: Int,
    val comment: String,
    val transactionDate: String
)