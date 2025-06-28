package com.yandex.finance.feature.outcome.api.domain.model

data class UiTransactionMainModel(
    val isIncome: Boolean?,
    val sumOfAllTransactions: Double,
    val transactions: List<UiTransactionModel>
) {

    companion object
}

