package com.yandex.finance.core.data.mapper

import com.yandex.finance.core.domain.model.account.*
import com.yandex.finance.core.network.account.model.*

// Domain to Network mappers
fun AccountDetailed.asNetworkModel() = NetworkAccountDetailed(
    id = id,
    userId = userId,
    name = name,
    balance = balance,
    currency = currency,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Account.asNetworkModel() = NetworkAccount(
    id = id,
    name = name,
    balance = balance,
    currency = currency
)

fun AccountWithoutId.asNetworkModel() = NetworkAccountWithoutId(
    name = name,
    balance = balance,
    currency = currency
)

fun MainAccount.asNetworkModel() = NetworkMainAccount(
    id = id,
    name = name,
    balance = balance,
    currency = currency,
    createdAt = createdAt,
    updatedAt = updatedAt,
    incomeStats = incomeStats.map { it.asNetworkModel() },
    expenseStats = expenseStats.map { it.asNetworkModel() }
)

fun AccountHistory.asNetworkModel() = NetworkAccountHistory(
    id = id,
    accountId = accountId,
    createdAt = createdAt,
    changeType = changeType,
    newState = newState.asNetworkModel(),
    changeTimestamp = changeTimestamp,
    previousState = previousState.asNetworkModel()
)

fun NewState.asNetworkModel() = NetworkNewState(
    id = id,
    name = name,
    balance = balance,
    currency = currency
)

fun PreviousState.asNetworkModel() = NetworkPreviousState(
    id = id,
    name = name,
    balance = balance,
    currency = currency
)

fun ExpenseStat.asNetworkModel() = NetworkExpenseStat(
    emoji = emoji,
    amount = amount,
    categoryId = categoryId,
    categoryName = categoryName
)

fun IncomeStat.asNetworkModel() = NetworkIncomeStat(
    emoji = emoji,
    amount = amount,
    categoryId = categoryId,
    categoryName = categoryName
)

// Network to Domain mappers
fun NetworkAccountDetailed.asExternalModel() = AccountDetailed(
    id = id,
    userId = userId,
    name = name,
    balance = balance,
    currency = currency,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun NetworkAccount.asExternalModel() = Account(
    id = id,
    name = name,
    balance = balance,
    currency = currency
)

fun NetworkAccountWithoutId.asExternalModel() = AccountWithoutId(
    name = name,
    balance = balance,
    currency = currency
)

fun NetworkMainAccount.asExternalModel() = MainAccount(
    id = id,
    name = name,
    balance = balance,
    currency = currency,
    createdAt = createdAt,
    updatedAt = updatedAt,
    incomeStats = incomeStats.map { it.asExternalModel() },
    expenseStats = expenseStats.map { it.asExternalModel() }
)

fun NetworkAccountHistory.asExternalModel() = AccountHistory(
    id = id,
    accountId = accountId,
    createdAt = createdAt,
    changeType = changeType,
    newState = newState.asExternalModel(),
    changeTimestamp = changeTimestamp,
    previousState = previousState.asExternalModel()
)

fun NetworkNewState.asExternalModel() = NewState(
    id = id,
    name = name,
    balance = balance,
    currency = currency
)

fun NetworkPreviousState.asExternalModel() = PreviousState(
    id = id,
    name = name,
    balance = balance,
    currency = currency
)

fun NetworkExpenseStat.asExternalModel() = ExpenseStat(
    emoji = emoji,
    amount = amount,
    categoryId = categoryId,
    categoryName = categoryName
)

fun NetworkIncomeStat.asExternalModel() = IncomeStat(
    emoji = emoji,
    amount = amount,
    categoryId = categoryId,
    categoryName = categoryName
)
