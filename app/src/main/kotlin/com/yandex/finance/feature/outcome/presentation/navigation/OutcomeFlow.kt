package com.yandex.finance.feature.outcome.presentation.navigation

import com.yandex.finance.core.ui.Graph
import kotlinx.serialization.Serializable

@Serializable
object OutcomeGraph : Graph

sealed interface OutcomeFlow {

    @Serializable
    data object OutcomeToday : OutcomeFlow

    @Serializable
    data object MyOutcome : OutcomeFlow

    @Serializable
    data object MyHistory : OutcomeFlow
}
