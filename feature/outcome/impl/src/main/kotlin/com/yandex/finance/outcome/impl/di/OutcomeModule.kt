package com.yandex.finance.outcome.impl.di

import com.yandex.finance.core.common.DoDispatchers
import com.yandex.finance.feature.outcome.api.domain.usecase.GetUiTransactionByPeriod
import com.yandex.finance.outcome.impl.domain.usecase.GetUiTransactionByPeriodImpl
import com.yandex.finance.outcome.impl.presentation.viewmodel.MyHistoryOutcomeViewModel
import com.yandex.finance.outcome.impl.presentation.viewmodel.OutcomeTodayViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val provideOutcomeModule = module {

    single<GetUiTransactionByPeriod> {
        GetUiTransactionByPeriodImpl(
            dispatcher = get(named(DoDispatchers.IO.name)),
            transactionRepository = get()
        )
    }

    viewModel<OutcomeTodayViewModel> {
        OutcomeTodayViewModel(
            id = get(),
            getUiTransactionByPeriod = get()
        )
    }

    viewModel<MyHistoryOutcomeViewModel> {
        MyHistoryOutcomeViewModel(
            id = get(),
            getUiTransactionByPeriod = get()
        )
    }
}
