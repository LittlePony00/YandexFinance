package com.yandex.finance.app.di.modules

import androidx.lifecycle.SavedStateHandle
import com.yandex.finance.app.di.MainActivityComponent
import com.yandex.finance.core.common.Dependencies
import com.yandex.finance.core.dagger.DependenciesKey
import com.yandex.finance.feature.transaction_analysis.api.di.TransactionAnalysisApiModule
import com.yandex.finance.feature.transaction_analysis.api.di.TransactionAnalysisDependencies
import com.yandex.finance.feature.transaction_analysis.impl.di.TransactionAnalysisImplModule
import com.yandex.finance.feature.transaction_analysis.api.di.TransactionAnalysisViewModelFactory
import com.yandex.finance.feature.transaction_analysis.api.di.TransactionAnalysisViewModelFactoryQualifier
import com.yandex.finance.feature.transaction_analysis.impl.presentation.viewmodel.TransactionAnalysisViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module(includes = [TransactionAnalysisDependenciesModule.FactoryModule::class])
interface TransactionAnalysisDependenciesModule {
    
    @Binds
    @IntoMap
    @DependenciesKey(TransactionAnalysisDependencies::class)
    fun bindTransactionAnalysisDependencies(impl: MainActivityComponent): Dependencies

    @Module
    object FactoryModule {
        
        @Provides
        @Singleton
        fun provideGetTransactionAnalysisUseCase(
            impl: com.yandex.finance.feature.transaction_analysis.impl.domain.usecase.GetTransactionAnalysisUseCaseImpl
        ): com.yandex.finance.feature.transaction_analysis.api.domain.usecase.GetTransactionAnalysisUseCase = impl
        
        @Provides
        @Singleton
        @TransactionAnalysisViewModelFactoryQualifier
        fun provideTransactionAnalysisViewModelFactory(
            factory: TransactionAnalysisViewModel.Factory
        ): TransactionAnalysisViewModelFactory = { savedStateHandle: SavedStateHandle ->
            factory.create(savedStateHandle)
        }
    }
} 