package com.yandex.finance.core.network.account.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkPreviousState(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
)
