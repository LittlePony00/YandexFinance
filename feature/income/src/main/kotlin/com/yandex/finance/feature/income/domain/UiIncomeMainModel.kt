package com.yandex.finance.feature.income.domain

data class UiIncomeMainModel(
    val sumOfAllIncomes: Double,
    val incomes: List<UiIncomeModel>
) {

    companion object {
        val initial
            get() = UiIncomeMainModel(
                sumOfAllIncomes = 100000.0,
                incomes = listOf()
            )
    }
}
