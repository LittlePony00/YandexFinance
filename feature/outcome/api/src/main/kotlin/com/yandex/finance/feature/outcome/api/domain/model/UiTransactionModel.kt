package com.yandex.finance.feature.outcome.api.domain.model

import com.yandex.finance.core.domain.model.CurrencyType
import com.yandex.finance.core.domain.model.account.Account
import com.yandex.finance.core.domain.model.category.Category
import com.yandex.finance.core.domain.model.transaction.Transaction

data class UiTransactionModel(
    val id: Int,
    val amount: String,
    val comment: String?,
    val account: UiAccount,
    val createdAt: String,
    val updatedAt: String,
    val category: UiCategory,
    val transactionDate: String
)

data class UiAccount(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: CurrencyType,
)

data class UiCategory(
    val id: Int,
    val name: String,
    val emoji: String? = null,
    val isIncome: Boolean,
)

fun Transaction.asUiModel() = UiTransactionModel(
    id = id,
    amount = amount,
    comment = comment,
    account = account.asUiModel(),
    createdAt = createdAt,
    updatedAt = updatedAt,
    category = category.asUiModel(),
    transactionDate = transactionDate
)

fun Account.asUiModel() = UiAccount(
    id = this.id,
    name = this.name,
    balance = this.balance,
    currency = CurrencyType.convertFromString(this.currency)
)

fun Category.asUiModel() = UiCategory(
    id = this.id,
    name = this.name,
    emoji = this.emoji,
    isIncome = this.isIncome
)

fun List<Transaction>.asUiModel(): List<UiTransactionModel> = this.map { it.asUiModel() }
