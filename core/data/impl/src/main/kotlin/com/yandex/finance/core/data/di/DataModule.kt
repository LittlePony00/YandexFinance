package com.yandex.finance.core.data.di

import com.yandex.finance.core.data.observer.NetworkConnectivityObserver
import com.yandex.finance.core.data.observer.NetworkConnectivityObserverImpl
import com.yandex.finance.core.data.repository.AccountRepositoryImpl
import com.yandex.finance.core.data.repository.CategoryRepositoryImpl
import com.yandex.finance.core.data.repository.TransactionRepositoryImpl
import com.yandex.finance.core.data.repository.AccountRepository
import com.yandex.finance.core.data.repository.CategoryRepository
import com.yandex.finance.core.data.repository.TransactionRepository
import org.koin.dsl.module

val provideDataModule = module {

    single<NetworkConnectivityObserver> {
        NetworkConnectivityObserverImpl(context = get())
    }

    single<AccountRepository> {
        AccountRepositoryImpl(
            accountService = get()
        )
    }

    single<CategoryRepository> {
        CategoryRepositoryImpl(
            categoryService = get()
        )
    }

    single<TransactionRepository> {
        TransactionRepositoryImpl(
            transactionService = get()
        )
    }
}
