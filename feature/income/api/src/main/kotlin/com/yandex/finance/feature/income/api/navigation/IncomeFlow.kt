package com.yandex.finance.feature.income.api.navigation

import com.yandex.finance.core.ui.navigation.Graph
import kotlinx.serialization.Serializable

@Serializable
object IncomeGraph : Graph

sealed interface IncomeFlow {

    @Serializable
    data object IncomeToday : IncomeFlow

    @Serializable
    data object MyHistory : IncomeFlow
}
