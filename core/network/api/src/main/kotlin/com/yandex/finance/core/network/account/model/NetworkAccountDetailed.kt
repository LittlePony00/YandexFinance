package com.yandex.finance.core.network.account.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkAccountDetailed(
    val id: Int,
    val userId: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val createdAt: String,
    val updatedAt: String,
)

@Serializable
data class NetworkAccount(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
)

@Serializable
data class NetworkAccountWithoutId(
    val name: String,
    val balance: String,
    val currency: String,
)
