package com.yandex.finance.feature.transaction_analysis.api.navigation

import com.yandex.finance.core.ui.navigation.Graph
import kotlinx.serialization.Serializable

@Serializable
object TransactionAnalysisGraph : Graph

sealed interface TransactionAnalysisFlow {

    @Serializable
    data class Analysis(
        val isIncome: Boolean,
        val startDate: String,
        val endDate: String
    ) : TransactionAnalysisFlow
} 