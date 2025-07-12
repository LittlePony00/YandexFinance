package com.yandex.finance.core.data.mapper

import com.yandex.finance.core.domain.model.transaction.CreatedTransaction
import com.yandex.finance.core.domain.model.transaction.Transaction
import com.yandex.finance.core.domain.model.transaction.TransactionWithoutId
import com.yandex.finance.core.network.formatter.DateTimeFormatter
import com.yandex.finance.core.network.transaction.model.NetworkCreatedTransaction
import com.yandex.finance.core.network.transaction.model.NetworkTransaction
import com.yandex.finance.core.network.transaction.model.NetworkTransactionWithoutId
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime

// Domain to Network mappers
fun Transaction.asNetworkModel() = NetworkTransaction(
    id = id,
    amount = amount,
    comment = comment,
    createdAt = Instant
        .parse(createdAt)
        .toLocalDateTime(TimeZone.UTC)
        .format(DateTimeFormatter.localDateTimeFormatter),
    updatedAt = Instant
        .parse(updatedAt)
        .toLocalDateTime(TimeZone.UTC)
        .format(DateTimeFormatter.localDateTimeFormatter),
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
    updatedAt = Instant
        .parse(updatedAt)
        .toLocalDateTime(TimeZone.UTC)
        .format(DateTimeFormatter.localDateTimeFormatter),
    createdAt = Instant
        .parse(createdAt)
        .toLocalDateTime(TimeZone.UTC)
        .format(DateTimeFormatter.localDateTimeFormatter),
    transactionDate = Instant
        .parse(transactionDate)
        .toLocalDateTime(TimeZone.UTC)
        .format(DateTimeFormatter.localDateTimeFormatter)
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
    id = id ?: 0,
    amount = amount ?: "",
    comment = comment ?: "",
    createdAt = createdAt ?: "",
    updatedAt = updatedAt ?: "",
    transactionDate = transactionDate ?: "",
    account = account?.asExternalModel() ?: com.yandex.finance.core.domain.model.account.Account(0, "", "", ""),
    category = category?.asExternalModel() ?: com.yandex.finance.core.domain.model.category.Category(0, "", null, false)
)

fun NetworkCreatedTransaction.asExternalModel() = CreatedTransaction(
    id = id ?: 0,
    amount = amount ?: "",
    accountId = accountId ?: 0,
    categoryId = categoryId ?: 0,
    comment = comment ?: "",
    updatedAt = updatedAt ?: "",
    createdAt = createdAt ?: "",
    transactionDate = transactionDate ?: ""
)

fun NetworkTransactionWithoutId.asExternalModel() = TransactionWithoutId(
    accountId = accountId,
    amount = amount,
    categoryId = categoryId,
    comment = comment ?: "",
    transactionDate = transactionDate
)
