package com.yandex.finance.app.di.modules

import com.yandex.finance.app.di.MainActivityComponent
import com.yandex.finance.core.common.Dependencies
import com.yandex.finance.core.dagger.DependenciesKey
import com.yandex.finance.feature.settings.api.di.SettingsDependencies
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SettingsDependenciesModule {
    
    @Binds
    @IntoMap
    @DependenciesKey(SettingsDependencies::class)
    fun bindSettingsDependencies(impl: MainActivityComponent): Dependencies
}
