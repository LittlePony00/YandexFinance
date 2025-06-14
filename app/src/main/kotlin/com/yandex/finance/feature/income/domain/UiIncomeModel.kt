package com.yandex.finance.feature.income.domain

data class UiIncomeModel(
    val id: Int,
    val sum: Int,
    val icon: String?,
    val subText: String?,
    val category: String
)