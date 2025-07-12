package com.yandex.finance.app.di.modules

import com.yandex.finance.app.di.MainActivityComponent
import com.yandex.finance.core.common.Dependencies
import com.yandex.finance.core.dagger.DependenciesKey
import com.yandex.finance.feature.income.api.di.IncomeDependencies
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface IncomeDependenciesModule {
    
    @Binds
    @IntoMap
    @DependenciesKey(IncomeDependencies::class)
    fun bindIncomeDependencies(impl: MainActivityComponent): Dependencies
}
