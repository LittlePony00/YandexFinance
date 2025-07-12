package com.yandex.finance.feature.transaction_edit.api.di

import androidx.lifecycle.SavedStateHandle
import com.yandex.finance.core.common.Dependencies
import com.yandex.finance.core.common.Id
import com.yandex.finance.core.data.repository.TransactionRepository
import com.yandex.finance.core.data.repository.CategoryRepository
import com.yandex.finance.core.data.repository.AccountRepository
import javax.inject.Qualifier

/**
 * Qualifier for TransactionEditViewModelFactory
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class TransactionEditViewModelFactoryQualifier

/**
 * Type alias for TransactionEditViewModel factory
 */
typealias TransactionEditViewModelFactory = (SavedStateHandle) -> Any

/**
 * Dependencies interface for TransactionEdit feature
 * Defines all dependencies that TransactionEdit feature needs from external modules
 */
interface TransactionEditDependencies : Dependencies {
    
    /**
     * User identifier for accessing transaction data
     */
    val id: Id
    
    /**
     * Repository for transaction operations (create, fetch, update, delete)
     */
    val transactionRepository: TransactionRepository
    
    /**
     * Repository for category operations (fetch categories by type)
     */
    val categoryRepository: CategoryRepository
    
    /**
     * Repository for account operations (fetch accounts)
     */
    val accountRepository: AccountRepository
    
    /**
     * Factory for creating TransactionEditViewModel with SavedStateHandle
     */
    @get:TransactionEditViewModelFactoryQualifier
    val transactionEditViewModelFactory: TransactionEditViewModelFactory
} 