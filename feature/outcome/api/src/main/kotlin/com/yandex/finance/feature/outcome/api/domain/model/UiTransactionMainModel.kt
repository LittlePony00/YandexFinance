package com.yandex.finance.feature.outcome.api.domain.model

data class UiTransactionMainModel(
    val isIncome: Boolean?,
    val sumOfAllTransactions: Double,
    val transactions: List<UiTransactionModel>
) {

    companion object {

        fun initialIncome() = UiTransactionMainModel(
            sumOfAllTransactions = 0.0,
            transactions = listOf(),
            isIncome = true
        )

        fun initialOutcome() = UiTransactionMainModel(
            sumOfAllTransactions = 0.0,
            transactions = listOf(),
            isIncome = false
        )
    }
}
