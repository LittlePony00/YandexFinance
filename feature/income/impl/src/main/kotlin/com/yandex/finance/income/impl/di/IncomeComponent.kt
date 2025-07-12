package com.yandex.finance.income.impl.di

import com.yandex.finance.feature.income.api.di.IncomeDependencies
import dagger.Component

/**
 * Internal Dagger component for Income feature
 * Uses dependencies from IncomeDependencies interface
 */
@Component(dependencies = [IncomeDependencies::class])
internal interface IncomeComponent {
    
    /**
     * Factory for creating IncomeComponent with dependencies
     */
    @Component.Factory
    interface Factory {
        fun create(deps: IncomeDependencies): IncomeComponent
    }
} 