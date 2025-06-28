package com.yandex.finance.outcome.impl.domain.model

import com.yandex.finance.feature.outcome.api.domain.model.UiTransactionMainModel

internal val UiTransactionMainModel.Companion.initialOutcome
    get() = UiTransactionMainModel(
        sumOfAllTransactions = 0.0,
        transactions = listOf(),
        isIncome = false
    )
