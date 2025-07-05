package com.yandex.finance.feature.outcome.api.domain.model

import com.yandex.finance.core.domain.model.CurrencyType

data class UiTransactionMainModel(
    val isIncome: Boolean?,
    val sumOfAllTransactions: Double,
    val currentCurrencyType: CurrencyType,
    val transactions: List<UiTransactionModel>
) {

    companion object
}
