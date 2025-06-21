package com.yandex.finance.core.domain.model.account

data class NewState(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
)