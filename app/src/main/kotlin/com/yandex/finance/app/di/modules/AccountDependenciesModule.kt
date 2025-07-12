package com.yandex.finance.app.di.modules

import com.yandex.finance.app.di.MainActivityComponent
import com.yandex.finance.core.common.Dependencies
import com.yandex.finance.core.dagger.DependenciesKey
import com.yandex.finance.feature.account.api.di.AccountDependencies
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface AccountDependenciesModule {
    
    @Binds
    @IntoMap
    @DependenciesKey(AccountDependencies::class)
    fun bindAccountDependencies(impl: MainActivityComponent): Dependencies
}
