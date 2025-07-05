package com.yandex.finance.core.domain.model.transaction

import com.yandex.finance.core.domain.model.account.Account
import com.yandex.finance.core.domain.model.category.Category

/**
 * Complete transaction information with associated account and category
 * 
 * @param id Unique transaction identifier
 * @param amount Transaction amount
 * @param comment Optional transaction comment
 * @param account Associated account information
 * @param createdAt Transaction creation timestamp
 * @param updatedAt Last update timestamp
 * @param category Transaction category information
 * @param transactionDate Date when transaction occurred
 */
data class Transaction(
    val id: Int,
    val amount: String,
    val comment: String?,
    val account: Account,
    val createdAt: String,
    val updatedAt: String,
    val category: Category,
    val transactionDate: String
)
