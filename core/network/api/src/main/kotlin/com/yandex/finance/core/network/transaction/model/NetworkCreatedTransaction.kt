package com.yandex.finance.core.network.transaction.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkCreatedTransaction(
    val id: Int? = null,
    val amount: String? = null,
    val accountId: Int? = null,
    val categoryId: Int? = null,
    val comment: String? = null,
    val updatedAt: String? = null,
    val createdAt: String? = null,
    val transactionDate: String? = null
)
