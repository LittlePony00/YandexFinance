package com.yandex.finance.core.domain.model.account

data class AccountDetailed(
    val id: Int,
    val userId: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val createdAt: String,
    val updatedAt: String,
)

data class Account(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
)

data class AccountWithoutId(
    val name: String,
    val balance: String,
    val currency: String,
)
