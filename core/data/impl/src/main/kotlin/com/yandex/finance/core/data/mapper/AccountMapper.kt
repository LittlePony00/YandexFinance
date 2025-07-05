package com.yandex.finance.core.data.mapper

import com.yandex.finance.core.domain.model.account.Account
import com.yandex.finance.core.domain.model.account.AccountDetailed
import com.yandex.finance.core.domain.model.account.AccountHistory
import com.yandex.finance.core.domain.model.account.AccountWithoutId
import com.yandex.finance.core.domain.model.account.ExpenseStat
import com.yandex.finance.core.domain.model.account.IncomeStat
import com.yandex.finance.core.domain.model.account.MainAccount
import com.yandex.finance.core.domain.model.account.NewState
import com.yandex.finance.core.domain.model.account.PreviousState
import com.yandex.finance.core.network.account.model.NetworkAccount
import com.yandex.finance.core.network.account.model.NetworkAccountDetailed
import com.yandex.finance.core.network.account.model.NetworkAccountHistory
import com.yandex.finance.core.network.account.model.NetworkAccountWithoutId
import com.yandex.finance.core.network.account.model.NetworkExpenseStat
import com.yandex.finance.core.network.account.model.NetworkIncomeStat
import com.yandex.finance.core.network.account.model.NetworkMainAccount
import com.yandex.finance.core.network.account.model.NetworkNewState
import com.yandex.finance.core.network.account.model.NetworkPreviousState
import com.yandex.finance.core.network.formatter.DateTimeFormatter
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime

// Domain to Network mappers
fun AccountDetailed.asNetworkModel() = NetworkAccountDetailed(
    id = id,
    userId = userId,
    name = name,
    balance = balance,
    currency = currency,
    createdAt = Instant
        .parse(createdAt)
        .toLocalDateTime(TimeZone.UTC)
        .format(DateTimeFormatter.localDateTimeFormatter),
    updatedAt = Instant
        .parse(updatedAt)
        .toLocalDateTime(TimeZone.UTC)
        .format(DateTimeFormatter.localDateTimeFormatter)
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
    createdAt = Instant
        .parse(createdAt)
        .toLocalDateTime(TimeZone.UTC)
        .format(DateTimeFormatter.localDateTimeFormatter),
    updatedAt = Instant
        .parse(updatedAt)
        .toLocalDateTime(TimeZone.UTC)
        .format(DateTimeFormatter.localDateTimeFormatter),
    incomeStats = incomeStats.map { it.asNetworkModel() },
    expenseStats = expenseStats.map { it.asNetworkModel() }
)

fun AccountHistory.asNetworkModel() = NetworkAccountHistory(
    id = id,
    accountId = accountId,
    createdAt = createdAt,
    changeType = changeType,
    newState = newState.asNetworkModel(),
    changeTimestamp = Instant
        .parse(createdAt)
        .toLocalDateTime(TimeZone.UTC)
        .format(DateTimeFormatter.localDateTimeFormatter),
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
