package com.yandex.finance.core.domain.model.account

/**
 * Account history record for tracking balance changes
 * 
 * @param id Unique history record identifier
 * @param accountId Associated account identifier
 * @param createdAt History record creation timestamp
 * @param changeType Type of balance change
 * @param newState Account state after change
 * @param changeTimestamp Timestamp when change occurred
 * @param previousState Account state before change
 */
data class AccountHistory(
    val id: Int,
    val accountId: Int,
    val createdAt: String,
    val changeType: String,
    val newState: NewState,
    val changeTimestamp: String,
    val previousState: PreviousState
)
