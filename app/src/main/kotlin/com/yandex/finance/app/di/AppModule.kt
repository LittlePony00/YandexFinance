package com.yandex.finance.app.di

import com.yandex.finance.app.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val provideAppModule = module {

    viewModel<MainViewModel> {
        MainViewModel(connectivityObserver = get())
    }
}
