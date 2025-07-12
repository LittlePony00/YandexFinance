package com.yandex.finance.outcome.impl.di

import com.yandex.finance.feature.outcome.api.di.OutcomeDependencies
import dagger.Component

/**
 * Internal Dagger component for Outcome feature
 * Uses dependencies from OutcomeDependencies interface
 */
@Component(dependencies = [OutcomeDependencies::class])
internal interface OutcomeComponent {
    
    /**
     * Factory for creating OutcomeComponent with dependencies
     */
    @Component.Factory
    interface Factory {
        fun create(deps: OutcomeDependencies): OutcomeComponent
    }
} 