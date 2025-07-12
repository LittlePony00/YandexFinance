package com.yandex.finance.feature.outcome.api.di

import com.yandex.finance.core.common.Dependencies
import com.yandex.finance.core.common.Id
import com.yandex.finance.feature.outcome.api.domain.usecase.GetUiCurrentCurrencyUseCase
import com.yandex.finance.feature.outcome.api.domain.usecase.GetUiTransactionByPeriodUseCase

/**
 * Dependencies interface for Outcome feature
 * Defines all dependencies that Outcome feature needs from external modules
 */
interface OutcomeDependencies : Dependencies {
    
    /**
     * User identifier for accessing transaction data
     */
    val id: Id
    
    /**
     * Use case for getting UI transaction data by period
     */
    val getUiTransactionByPeriodUseCase: GetUiTransactionByPeriodUseCase
    
    /**
     * Use case for getting UI current currency
     */
    val getUiCurrentCurrencyUseCase: GetUiCurrentCurrencyUseCase
} 