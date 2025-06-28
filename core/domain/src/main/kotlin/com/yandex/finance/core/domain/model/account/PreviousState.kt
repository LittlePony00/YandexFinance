package com.yandex.finance.core.domain.model.account

/**
 * Account state before a balance change
 * 
 * @param id Account identifier
 * @param name Account display name
 * @param balance Previous account balance
 * @param currency Account currency code
 */
data class PreviousState(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
)
