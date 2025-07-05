package com.yandex.finance.core.network.account.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkIncomeStat(
    val emoji: String,
    val amount: String,
    val categoryId: Int,
    val categoryName: String
)
