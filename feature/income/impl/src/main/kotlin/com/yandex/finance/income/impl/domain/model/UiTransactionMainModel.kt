package com.yandex.finance.income.impl.domain.model

import com.yandex.finance.core.domain.model.CurrencyType
import com.yandex.finance.feature.outcome.api.domain.model.UiTransactionMainModel

internal val UiTransactionMainModel.Companion.initialIncome
    get() = UiTransactionMainModel(
        sumOfAllTransactions = 0.0,
        transactions = listOf(),
        isIncome = true,
        currentCurrencyType = CurrencyType.RUB
    )
