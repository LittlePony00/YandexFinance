package com.yandex.finance.core.network.category.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkCategory(
    val id: Int,
    val name: String,
    val emoji: String? = null,
    val isIncome: Boolean,
)
