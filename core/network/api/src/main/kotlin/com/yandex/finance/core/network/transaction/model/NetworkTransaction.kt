package com.yandex.finance.core.network.transaction.model

import com.yandex.finance.core.network.account.model.NetworkAccount
import com.yandex.finance.core.network.category.model.NetworkCategory
import kotlinx.serialization.Serializable

@Serializable
data class NetworkTransaction(
    val id: Int? = null,
    val amount: String? = null,
    val comment: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val transactionDate: String? = null,
    val account: NetworkAccount? = null,
    val category: NetworkCategory? = null
)
