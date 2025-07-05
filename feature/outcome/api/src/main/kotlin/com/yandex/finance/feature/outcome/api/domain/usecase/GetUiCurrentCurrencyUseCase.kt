package com.yandex.finance.feature.outcome.api.domain.usecase

import com.yandex.finance.core.domain.model.CurrencyType

interface GetUiCurrentCurrencyUseCase {

    suspend operator fun invoke(): CurrencyType
}