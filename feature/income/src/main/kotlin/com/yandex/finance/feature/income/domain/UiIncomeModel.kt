package com.yandex.finance.feature.income.domain

import com.yandex.finance.core.domain.model.account.Account
import com.yandex.finance.core.domain.model.category.Category
import com.yandex.finance.core.domain.model.transaction.Transaction

data class UiIncomeModel(
    val id: Int,
    val amount: String,
    val comment: String?,
    val account: UiAccount,
    val createdAt: String,
    val updatedAt: String,
    val category: UiCategory,
    val transactionDate: String
)

enum class CurrencyType(val type: String) {
    RUB(type = "₽"),
    USD(type ="$"),
    EUR(type = "€"),
    ;

    companion object {

        fun convertFromString(type: String): CurrencyType {
            return when(type) {
                "USD" -> USD
                "EUR" -> EUR
                else -> RUB
            }
        }
    }
}

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

fun Transaction.asUiModel() = UiIncomeModel(
    id = this.id,
    amount = this.amount,
    comment = this.comment,
    account = this.account.asUiModel(),
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    category = this.category.asUiModel(),
    transactionDate = this.transactionDate
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

fun List<Transaction>.asUiModel(): List<UiIncomeModel> = this.map { it.asUiModel() }