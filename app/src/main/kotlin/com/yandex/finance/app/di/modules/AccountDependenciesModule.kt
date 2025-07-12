package com.yandex.finance.app.di.modules

import androidx.lifecycle.SavedStateHandle
import com.yandex.finance.app.di.MainActivityComponent
import com.yandex.finance.core.common.Dependencies
import com.yandex.finance.core.dagger.DependenciesKey
import com.yandex.finance.feature.account.api.di.AccountDependencies
import com.yandex.finance.feature.account.api.di.MyAccountEditViewModelFactory
import com.yandex.finance.feature.account.impl.presentation.viewmodel.MyAccountEditViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module(includes = [AccountDependenciesModule.FactoryModule::class])
interface AccountDependenciesModule {
    
    @Binds
    @IntoMap
    @DependenciesKey(AccountDependencies::class)
    fun bindAccountDependencies(impl: MainActivityComponent): Dependencies

    @Module
    object FactoryModule {
        
        @Provides
        @Singleton
        fun provideMyAccountEditViewModelFactory(
            factory: MyAccountEditViewModel.Factory
        ): MyAccountEditViewModelFactory = { savedStateHandle: SavedStateHandle ->
            factory.create(savedStateHandle)
        }
    }
}
