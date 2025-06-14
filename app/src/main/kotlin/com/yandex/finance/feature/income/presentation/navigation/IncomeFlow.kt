package com.yandex.finance.feature.income.presentation.navigation

import com.yandex.finance.core.ui.Graph
import kotlinx.serialization.Serializable

@Serializable
object IncomeGraph : Graph

sealed interface IncomeFlow {

    @Serializable
    data object IncomeToday : IncomeFlow
}
