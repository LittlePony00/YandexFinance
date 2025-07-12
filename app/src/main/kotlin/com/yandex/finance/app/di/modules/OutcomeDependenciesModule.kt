package com.yandex.finance.app.di.modules

import com.yandex.finance.app.di.MainActivityComponent
import com.yandex.finance.core.common.Dependencies
import com.yandex.finance.core.dagger.DependenciesKey
import com.yandex.finance.feature.outcome.api.di.OutcomeDependencies
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface OutcomeDependenciesModule {
    
    @Binds
    @IntoMap
    @DependenciesKey(OutcomeDependencies::class)
    fun bindOutcomeDependencies(impl: MainActivityComponent): Dependencies
}
