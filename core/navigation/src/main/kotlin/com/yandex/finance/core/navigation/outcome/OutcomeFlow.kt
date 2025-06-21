package com.yandex.finance.core.navigation.outcome

import kotlinx.serialization.Serializable

@Serializable
object OutcomeGraph : com.yandex.finance.core.navigation.Graph

sealed interface OutcomeFlow {

    @Serializable
    data object OutcomeToday : OutcomeFlow

    @Serializable
    data object MyOutcome : OutcomeFlow

    @Serializable
    data object MyHistory : OutcomeFlow
}
