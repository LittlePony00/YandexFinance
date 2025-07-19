package com.yandex.finance.feature.transaction_analysis.api.di

import androidx.lifecycle.SavedStateHandle
import com.yandex.finance.core.common.Dependencies
import com.yandex.finance.core.common.Id
import com.yandex.finance.feature.transaction_analysis.api.domain.usecase.GetTransactionAnalysisUseCase
import javax.inject.Qualifier

/**
 * Qualifier for TransactionAnalysisViewModelFactory
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class TransactionAnalysisViewModelFactoryQualifier

/**
 * Type alias for TransactionAnalysisViewModel factory
 */
typealias TransactionAnalysisViewModelFactory = (SavedStateHandle) -> Any

/**
 * Dependencies interface for TransactionAnalysis feature
 * Defines all dependencies that TransactionAnalysis feature needs from external modules
 */
interface TransactionAnalysisDependencies : Dependencies {
    
    /**
     * User identifier for accessing transaction data
     */
    val id: Id
    
    /**
     * Use case for getting transaction analysis data
     */
    val getTransactionAnalysisUseCase: GetTransactionAnalysisUseCase
    
    /**
     * Factory for creating TransactionAnalysisViewModel with SavedStateHandle
     */
    @get:TransactionAnalysisViewModelFactoryQualifier
    val transactionAnalysisViewModelFactory: TransactionAnalysisViewModelFactory
} 