package com.yandex.finance.core.navigation.income

import kotlinx.serialization.Serializable

@Serializable
object IncomeGraph : com.yandex.finance.core.navigation.Graph

sealed interface IncomeFlow {

    @Serializable
    data object IncomeToday : IncomeFlow

    @Serializable
    data object MyHistory : IncomeFlow
}
