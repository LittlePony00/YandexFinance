package com.yandex.finance.core.network.transaction.model

import com.yandex.finance.core.network.account.model.NetworkAccount
import com.yandex.finance.core.network.category.model.NetworkCategory
import kotlinx.serialization.Serializable

@Serializable
data class NetworkTransaction(
    val id: Int,
    val amount: String,
    val comment: String?,
    val createdAt: String,
    val updatedAt: String,
    val transactionDate: String,
    val account: NetworkAccount,
    val category: NetworkCategory
)
