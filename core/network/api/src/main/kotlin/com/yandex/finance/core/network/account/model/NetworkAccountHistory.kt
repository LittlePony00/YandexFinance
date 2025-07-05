package com.yandex.finance.core.network.account.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkAccountHistory(
    val id: Int,
    val accountId: Int,
    val createdAt: String,
    val changeType: String,
    val newState: NetworkNewState,
    val changeTimestamp: String,
    val previousState: NetworkPreviousState
)
