package com.yandex.finance.app.di

import com.yandex.finance.app.presentation.viewmodel.MainViewModel
import com.yandex.finance.core.common.Id
import com.yandex.finance.core.common.UserId
import com.yandex.finance.core.network.impl.BuildConfig
import com.yandex.finance.core.ui.util.UiDateTimeFormatter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val provideAppModule = module {

    viewModel<MainViewModel> {
        MainViewModel(connectivityObserver = get())
    }

    single<UserId> {
        BuildConfig.USER_ID
    }

    single<Id> {
        BuildConfig.ID
    }

    single<UiDateTimeFormatter> {
        UiDateTimeFormatter(context = get())
    }
}
