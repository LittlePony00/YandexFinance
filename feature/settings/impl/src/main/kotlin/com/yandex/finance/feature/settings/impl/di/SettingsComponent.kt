package com.yandex.finance.feature.settings.impl.di

import com.yandex.finance.feature.settings.api.di.SettingsDependencies
import com.yandex.finance.feature.settings.impl.presentation.viewmodel.SettingsViewModel
import dagger.Component

/**
 * Internal Dagger component for Settings feature
 * Uses dependencies from SettingsDependencies interface
 */
@Component(dependencies = [SettingsDependencies::class])
internal interface SettingsComponent {
    
    fun inject(viewModel: SettingsViewModel)
    
    /**
     * Factory for creating SettingsComponent with dependencies
     */
    @Component.Factory
    interface Factory {
        fun create(deps: SettingsDependencies): SettingsComponent
    }
} 