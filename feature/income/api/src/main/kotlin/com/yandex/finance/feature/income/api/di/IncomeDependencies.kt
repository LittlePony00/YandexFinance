package com.yandex.finance.feature.income.api.di

import com.yandex.finance.core.common.Dependencies
import com.yandex.finance.core.common.Id
import com.yandex.finance.feature.outcome.api.domain.usecase.GetUiTransactionByPeriodUseCase

/**
 * Dependencies interface for Income feature
 * Defines all dependencies that Income feature needs from external modules
 */
interface IncomeDependencies : Dependencies {
    
    /**
     * User identifier for accessing transaction data
     */
    val id: Id
    
    /**
     * Use case for getting UI transaction data by period (shared with outcome)
     */
    val getUiTransactionByPeriodUseCase: GetUiTransactionByPeriodUseCase
} 