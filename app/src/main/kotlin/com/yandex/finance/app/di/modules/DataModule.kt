package com.yandex.finance.app.di.modules

import android.content.Context
import com.yandex.finance.core.data.observer.NetworkConnectivityObserver
import com.yandex.finance.core.data.observer.NetworkConnectivityObserverImpl
import com.yandex.finance.core.data.repository.AccountRepository
import com.yandex.finance.core.data.repository.AccountRepositoryImpl
import com.yandex.finance.core.data.repository.CategoryRepository
import com.yandex.finance.core.data.repository.CategoryRepositoryImpl
import com.yandex.finance.core.data.repository.TransactionRepository
import com.yandex.finance.core.data.repository.TransactionRepositoryImpl
import com.yandex.finance.core.network.account.service.AccountService
import com.yandex.finance.core.network.category.service.CategoryService
import com.yandex.finance.core.network.transaction.service.TransactionService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DataModule {
    
    @Provides
    @Singleton
    fun provideNetworkConnectivityObserver(context: Context): NetworkConnectivityObserver {
        return NetworkConnectivityObserverImpl(context)
    }
    
    @Provides
    @Singleton
    fun provideAccountRepository(accountService: AccountService): AccountRepository {
        return AccountRepositoryImpl(accountService)
    }
    
    @Provides
    @Singleton
    fun provideCategoryRepository(categoryService: CategoryService): CategoryRepository {
        return CategoryRepositoryImpl(categoryService)
    }
    
    @Provides
    @Singleton
    fun provideTransactionRepository(transactionService: TransactionService): TransactionRepository {
        return TransactionRepositoryImpl(transactionService)
    }
} 