package com.yandex.finance.feature.income.di

import com.yandex.finance.core.common.DoDispatchers
import com.yandex.finance.feature.income.domain.GetUiIncomeTodayModelByPeriodUseCase
import com.yandex.finance.feature.income.presentation.viewmodel.IncomeTodayViewModel
import com.yandex.finance.feature.income.presentation.viewmodel.MyHistoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val provideIncomeModule = module {

    single<GetUiIncomeTodayModelByPeriodUseCase> {
        GetUiIncomeTodayModelByPeriodUseCase(
            dispatcher = get(named(DoDispatchers.IO.name)),
            transactionRepository = get()
        )
    }

    viewModel<IncomeTodayViewModel> {
        IncomeTodayViewModel(
            id = get(),
            getUiIncomeTodayModelByPeriodUseCase = get()
        )
    }

    viewModel<MyHistoryViewModel> {
        MyHistoryViewModel(
            id = get(),
            getUiIncomeTodayModelByPeriodUseCase = get()
        )
    }
}
