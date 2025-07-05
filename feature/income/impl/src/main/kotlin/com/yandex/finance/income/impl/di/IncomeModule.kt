package com.yandex.finance.income.impl.di

import com.yandex.finance.income.impl.presentation.viewmodel.IncomeTodayViewModel
import com.yandex.finance.income.impl.presentation.viewmodel.MyHistoryIncomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val provideIncomeModule = module {

    viewModel<IncomeTodayViewModel> {
        IncomeTodayViewModel(
            id = get(),
            getUiTransactionByPeriodUseCase = get()
        )
    }

    viewModel<MyHistoryIncomeViewModel> {
        MyHistoryIncomeViewModel(
            id = get(),
            getUiTransactionByPeriodUseCase = get()
        )
    }
}
