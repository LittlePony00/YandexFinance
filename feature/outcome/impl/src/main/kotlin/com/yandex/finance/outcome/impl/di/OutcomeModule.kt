package com.yandex.finance.outcome.impl.di

import com.yandex.finance.core.common.DoDispatchers
import com.yandex.finance.feature.outcome.api.domain.usecase.GetUiCurrentCurrencyUseCase
import com.yandex.finance.feature.outcome.api.domain.usecase.GetUiTransactionByPeriodUseCase
import com.yandex.finance.outcome.impl.domain.usecase.GetUiCurrentCurrencyUseCaseImpl
import com.yandex.finance.outcome.impl.domain.usecase.GetUiTransactionByPeriodUseCaseImpl
import com.yandex.finance.outcome.impl.presentation.viewmodel.MyHistoryOutcomeViewModel
import com.yandex.finance.outcome.impl.presentation.viewmodel.OutcomeTodayViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val provideOutcomeModule = module {

    factory<GetUiTransactionByPeriodUseCase> {
        GetUiTransactionByPeriodUseCaseImpl(
            dispatcher = get(named(DoDispatchers.IO.name)),
            transactionRepository = get(),
            getUiCurrentCurrencyUseCase = get()
        )
    }
    
    factory<GetUiCurrentCurrencyUseCase> {
        GetUiCurrentCurrencyUseCaseImpl(
            id = get(),
            accountRepository = get()
        )
    }

    viewModel<OutcomeTodayViewModel> {
        OutcomeTodayViewModel(
            id = get(),
            getUiTransactionByPeriodUseCase = get()
        )
    }

    viewModel<MyHistoryOutcomeViewModel> {
        MyHistoryOutcomeViewModel(
            id = get(),
            getUiTransactionByPeriodUseCase = get()
        )
    }
}
