package com.yandex.finance.app.di.modules

import androidx.lifecycle.SavedStateHandle
import com.yandex.finance.app.di.MainActivityComponent
import com.yandex.finance.core.common.Dependencies
import com.yandex.finance.core.dagger.DependenciesKey
import com.yandex.finance.feature.transaction_edit.api.di.TransactionEditDependencies
import com.yandex.finance.feature.transaction_edit.api.di.TransactionEditViewModelFactory
import com.yandex.finance.feature.transaction_edit.api.di.TransactionEditViewModelFactoryQualifier
import com.yandex.finance.feature.transaction_edit.impl.presentation.viewmodel.TransactionEditViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module(includes = [TransactionEditDependenciesModule.FactoryModule::class])
interface TransactionEditDependenciesModule {
    
    @Binds
    @IntoMap
    @DependenciesKey(TransactionEditDependencies::class)
    fun bindTransactionEditDependencies(impl: MainActivityComponent): Dependencies

    @Module
    object FactoryModule {
        
        @Provides
        @Singleton
        @TransactionEditViewModelFactoryQualifier
        fun provideTransactionEditViewModelFactory(
            factory: TransactionEditViewModel.Factory
        ): TransactionEditViewModelFactory = { savedStateHandle: SavedStateHandle ->
            factory.create(savedStateHandle)
        }
    }
} 