package com.yandex.finance.feature.outcome.api.navigation

import com.yandex.finance.core.ui.navigation.Graph
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
