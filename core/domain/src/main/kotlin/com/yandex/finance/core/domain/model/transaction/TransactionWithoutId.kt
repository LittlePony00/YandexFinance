package com.yandex.finance.core.domain.model.transaction

/**
 * Transaction data for creation without identifier
 * 
 * @param accountId Associated account identifier
 * @param amount Transaction amount
 * @param categoryId Transaction category identifier
 * @param comment Transaction comment
 * @param transactionDate Date when transaction occurred
 */
data class TransactionWithoutId(
    val accountId: Int,
    val amount: String,
    val categoryId: Int,
    val comment: String,
    val transactionDate: String
)