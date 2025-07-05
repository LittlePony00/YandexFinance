package com.yandex.finance.core.domain.model.account

/**
 * Detailed account information with full metadata
 * 
 * @param id Unique account identifier
 * @param userId Owner user identifier
 * @param name Account display name
 * @param balance Current account balance
 * @param currency Account currency code
 * @param createdAt Account creation timestamp
 * @param updatedAt Last update timestamp
 */
data class AccountDetailed(
    val id: Int,
    val userId: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val createdAt: String,
    val updatedAt: String,
)

/**
 * Basic account information
 * 
 * @param id Unique account identifier
 * @param name Account display name
 * @param balance Current account balance
 * @param currency Account currency code
 */
data class Account(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
)

/**
 * Account data for creation without identifier
 * 
 * @param name Account display name
 * @param balance Initial account balance
 * @param currency Account currency code
 */
data class AccountWithoutId(
    val name: String,
    val balance: String,
    val currency: String,
)
