package com.yandex.finance.feature.income.di

import com.yandex.finance.feature.income.presentation.viewmodel.IncomeTodayViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val provideIncomeModule = module {

    viewModel<IncomeTodayViewModel> {
        IncomeTodayViewModel(
            transactionRepository = get()
        )
    }
}
