package com.yandex.finance.core.domain.model.account

/**
 * Account state after a balance change
 * 
 * @param id Account identifier
 * @param name Account display name
 * @param balance New account balance
 * @param currency Account currency code
 */
data class NewState(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
)
