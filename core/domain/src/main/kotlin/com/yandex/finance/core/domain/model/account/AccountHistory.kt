package com.yandex.finance.core.domain.model.account

data class AccountHistory(
    val id: Int,
    val accountId: Int,
    val createdAt: String,
    val changeType: String,
    val newState: NewState,
    val changeTimestamp: String,
    val previousState: PreviousState
)