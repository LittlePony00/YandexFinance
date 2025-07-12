package com.yandex.finance.feature.account.impl.di

import com.yandex.finance.feature.account.api.di.AccountDependencies
import dagger.Component

/**
 * Internal Dagger component for Account feature
 * Uses dependencies from AccountDependencies interface
 */
@Component(dependencies = [AccountDependencies::class])
internal interface AccountComponent {
    
    /**
     * Factory for creating AccountComponent with dependencies
     */
    @Component.Factory
    interface Factory {
        fun create(deps: AccountDependencies): AccountComponent
    }
}
