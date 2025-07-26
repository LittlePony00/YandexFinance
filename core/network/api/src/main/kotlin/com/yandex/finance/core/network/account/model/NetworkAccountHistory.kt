package com.yandex.finance.core.network.account.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkHistory(
    val id: Int,
    val accountId: Int,
    val createdAt: String,
    val changeType: String,
    val newState: NetworkNewState?,
    val changeTimestamp: String,
    val previousState: NetworkPreviousState?
)

@Serializable
data class NetworkAccountHistory(
    val accountId: Int,
    val accountName: String,
    val currency: String,
    val currentBalance: Double,
    val history: List<NetworkHistory>
)
