package com.yandex.finance.core.network.account.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkMainAccount(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val createdAt: String,
    val updatedAt: String,
    val incomeStats: List<NetworkIncomeStat>,
    val expenseStats: List<NetworkExpenseStat>
)
