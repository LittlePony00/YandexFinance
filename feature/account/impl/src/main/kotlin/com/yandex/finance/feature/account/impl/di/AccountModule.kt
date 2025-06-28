package com.yandex.finance.feature.account.impl.di

import com.yandex.finance.feature.account.impl.presentation.viewmodel.MyAccountViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val provideAccountModule = module { 
    
    viewModel<MyAccountViewModel> {
        MyAccountViewModel(
            id = get(),
            accountRepository = get()
        )
    }
}
