package com.yandex.finance.app.di.modules

import com.yandex.finance.core.common.Id
import com.yandex.finance.core.data.repository.AccountRepository
import com.yandex.finance.core.data.repository.TransactionRepository
import com.yandex.finance.feature.outcome.api.domain.usecase.GetUiCurrentCurrencyUseCase
import com.yandex.finance.feature.outcome.api.domain.usecase.GetUiTransactionByPeriodUseCase
import com.yandex.finance.outcome.impl.domain.usecase.GetUiCurrentCurrencyUseCaseImpl
import com.yandex.finance.outcome.impl.domain.usecase.GetUiTransactionByPeriodUseCaseImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named
import javax.inject.Singleton

@Module
object OutcomeUseCaseModule {
    
    @Provides
    @Singleton
    fun provideGetUiCurrentCurrencyUseCase(
        id: Id,
        accountRepository: AccountRepository
    ): GetUiCurrentCurrencyUseCase {
        return GetUiCurrentCurrencyUseCaseImpl(id, accountRepository)
    }
    
    @Provides
    @Singleton
    fun provideGetUiTransactionByPeriodUseCase(
        @Named("IO") dispatcher: CoroutineDispatcher,
        transactionRepository: TransactionRepository,
        getUiCurrentCurrencyUseCase: GetUiCurrentCurrencyUseCase
    ): GetUiTransactionByPeriodUseCase {
        return GetUiTransactionByPeriodUseCaseImpl(
            dispatcher = dispatcher,
            transactionRepository = transactionRepository,
            getUiCurrentCurrencyUseCase = getUiCurrentCurrencyUseCase
        )
    }
} 