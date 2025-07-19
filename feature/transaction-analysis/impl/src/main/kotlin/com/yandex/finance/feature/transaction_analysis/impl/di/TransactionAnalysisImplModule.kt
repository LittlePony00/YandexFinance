package com.yandex.finance.feature.transaction_analysis.impl.di

import com.yandex.finance.feature.transaction_analysis.api.domain.usecase.GetTransactionAnalysisUseCase
import com.yandex.finance.feature.transaction_analysis.impl.domain.usecase.GetTransactionAnalysisUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface TransactionAnalysisImplModule {

    @Binds
    fun bindGetTransactionAnalysisUseCase(
        impl: GetTransactionAnalysisUseCaseImpl
    ): GetTransactionAnalysisUseCase
} 