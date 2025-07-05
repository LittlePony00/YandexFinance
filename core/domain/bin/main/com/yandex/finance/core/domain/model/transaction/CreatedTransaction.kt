package com.yandex.finance.core.domain.model.transaction

/**
 * Transaction data after successful creation
 * 
 * @param id Unique transaction identifier
 * @param amount Transaction amount
 * @param accountId Associated account identifier
 * @param categoryId Transaction category identifier
 * @param comment Transaction comment
 * @param updatedAt Last update timestamp
 * @param createdAt Transaction creation timestamp
 * @param transactionDate Date when transaction occurred
 */
data class CreatedTransaction(
    val id: Int,
    val amount: String,
    val accountId: Int,
    val categoryId: Int,
    val comment: String,
    val updatedAt: String,
    val createdAt: String,
    val transactionDate: String
)
