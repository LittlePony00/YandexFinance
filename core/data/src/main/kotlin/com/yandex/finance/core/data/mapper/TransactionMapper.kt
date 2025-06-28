package com.yandex.finance.core.data.mapper

import com.yandex.finance.core.domain.model.transaction.CreatedTransaction
import com.yandex.finance.core.domain.model.transaction.Transaction
import com.yandex.finance.core.domain.model.transaction.TransactionWithoutId
import com.yandex.finance.core.network.transaction.model.NetworkCreatedTransaction
import com.yandex.finance.core.network.transaction.model.NetworkTransaction
import com.yandex.finance.core.network.transaction.model.NetworkTransactionWithoutId

// Domain to Network mappers
fun Transaction.asNetworkModel() = NetworkTransaction(
    id = id,
    amount = amount,
    comment = comment,
    createdAt = createdAt,
    updatedAt = updatedAt,
    transactionDate = transactionDate,
    account = account.asNetworkModel(),
    category = category.asNetworkModel()
)

fun CreatedTransaction.asNetworkModel() = NetworkCreatedTransaction(
    id = id,
    amount = amount,
    accountId = accountId,
    categoryId = categoryId,
    comment = comment,
    updatedAt = updatedAt,
    createdAt = createdAt,
    transactionDate = transactionDate
)

fun TransactionWithoutId.asNetworkModel() = NetworkTransactionWithoutId(
    accountId = accountId,
    amount = amount,
    categoryId = categoryId,
    comment = comment,
    transactionDate = transactionDate
)

// Network to Domain mappers
fun NetworkTransaction.asExternalModel() = Transaction(
    id = id,
    amount = amount,
    comment = comment,
    createdAt = createdAt,
    updatedAt = updatedAt,
    transactionDate = transactionDate,
    account = account.asExternalModel(),
    category = category.asExternalModel()
)

fun NetworkCreatedTransaction.asExternalModel() = CreatedTransaction(
    id = id,
    amount = amount,
    accountId = accountId,
    categoryId = categoryId,
    comment = comment,
    updatedAt = updatedAt,
    createdAt = createdAt,
    transactionDate = transactionDate
)

fun NetworkTransactionWithoutId.asExternalModel() = TransactionWithoutId(
    accountId = accountId,
    amount = amount,
    categoryId = categoryId,
    comment = comment ?: "",
    transactionDate = transactionDate
)
