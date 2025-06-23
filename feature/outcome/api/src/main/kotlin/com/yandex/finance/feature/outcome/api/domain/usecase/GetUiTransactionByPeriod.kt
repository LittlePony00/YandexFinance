package com.yandex.finance.feature.outcome.api.domain.usecase

import com.yandex.finance.core.domain.UseCase
import com.yandex.finance.feature.outcome.api.domain.model.UiTransactionMainModel

interface GetUiTransactionByPeriod : UseCase {

    suspend operator fun invoke(
        id: String,
        startDate: String,
        endDate: String,
        isIncome: Boolean? = null
    ): Result<UiTransactionMainModel>
}
