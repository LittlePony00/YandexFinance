package com.yandex.finance.feature.outcome.di

import com.yandex.finance.core.common.DoDispatchers
import com.yandex.finance.feature.outcome.domain.GetUiOutcomeTodayModelByPeriodUseCase
import com.yandex.finance.feature.outcome.presentation.viewmodel.MyHistoryViewModel
import com.yandex.finance.feature.outcome.presentation.viewmodel.OutcomeTodayViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val provideOutcomeModule = module {

    single<GetUiOutcomeTodayModelByPeriodUseCase> {
        GetUiOutcomeTodayModelByPeriodUseCase(
            dispatcher = get(named(DoDispatchers.IO.name)),
            transactionRepository = get()
        )
    }

    viewModel<OutcomeTodayViewModel> {
        OutcomeTodayViewModel(
            id = get(),
            getUiOutcomeTodayModelByPeriodUseCase = get()
        )
    }

    viewModel<MyHistoryViewModel> {
        MyHistoryViewModel(
            id = get(),
            getUiOutcomeTodayModelByPeriodUseCase = get()
        )
    }
}
