package com.yandex.finance.feature.outcome.di

import com.yandex.finance.feature.outcome.presentation.viewmodel.OutcomeTodayViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val provideOutcomeModule = module {

    viewModel<OutcomeTodayViewModel> {
        OutcomeTodayViewModel(
            transactionRepository = get()
        )
    }
}
