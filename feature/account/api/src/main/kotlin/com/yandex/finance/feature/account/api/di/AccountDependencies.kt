package com.yandex.finance.feature.account.api.di

import com.yandex.finance.core.common.Dependencies
import com.yandex.finance.core.common.Id
import com.yandex.finance.core.data.repository.AccountRepository

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
} 