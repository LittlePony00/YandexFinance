package com.yandex.finance.core.network.transaction.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkTransactionWithoutId(
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: String,
    val comment: String?
)
