package com.yandex.finance.feature.account.api.di

import androidx.lifecycle.SavedStateHandle
import com.yandex.finance.core.common.Dependencies
import com.yandex.finance.core.common.Id
import com.yandex.finance.core.data.repository.AccountRepository

/**
 * Type alias for MyAccountEditViewModel factory
 */
typealias MyAccountEditViewModelFactory = (SavedStateHandle) -> Any

/**
 * Dependencies interface for Account feature
 * Defines all dependencies that Account feature needs from external modules
 */
interface AccountDependencies : Dependencies {
    
    /**
     * User identifier for accessing account data
     */
    val id: Id
    
    /**
     * Repository for account operations (fetch, update, delete)
     */
    val accountRepository: AccountRepository
    
    /**
     * Factory for creating MyAccountEditViewModel with SavedStateHandle
     */
    val myAccountEditViewModelFactory: MyAccountEditViewModelFactory
} 