package com.yandex.finance.feature.income.domain

data class UiIncomeTodayModel(
    val sumOfAllIncomes: Int,
    val incomes: List<UiIncomeModel>
) {

    companion object {
        val initial
            get() = UiIncomeTodayModel(
                sumOfAllIncomes = 100000,
                incomes = listOf()
            )
    }
}
