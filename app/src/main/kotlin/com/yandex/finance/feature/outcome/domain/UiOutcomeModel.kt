package com.yandex.finance.feature.outcome.domain

data class UiOutcomeModel(
    val id: Int,
    val sum: Int,
    val icon: String?,
    val subText: String,
    val category: String
)