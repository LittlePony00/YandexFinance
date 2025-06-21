package com.yandex.finance.core.network.transaction.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkTransactionWithoutId(
    val accountId: Int,
    val amount: String,
    val categoryId: Int,
    val comment: String?,
    val transactionDate: String
)
