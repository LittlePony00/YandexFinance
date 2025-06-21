package com.yandex.finance.core.network.transaction.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkCreatedTransaction(
    val id: Int,
    val amount: String,
    val accountId: Int,
    val categoryId: Int,
    val comment: String,
    val updatedAt: String,
    val createdAt: String,
    val transactionDate: String
)
