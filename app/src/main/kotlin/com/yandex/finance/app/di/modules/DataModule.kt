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
import com.yandex.finance.core.data.sync.SyncStatusRepository
import com.yandex.finance.core.data.sync.SyncStatusRepositoryImpl
import com.yandex.finance.core.localdb.dao.AccountDao
import com.yandex.finance.core.localdb.dao.CategoryDao
import com.yandex.finance.core.localdb.dao.SyncMetadataDao
import com.yandex.finance.core.localdb.dao.TransactionDao
import com.yandex.finance.core.localdb.database.YandexFinanceDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindSyncStatusRepository(
        syncStatusRepositoryImpl: SyncStatusRepositoryImpl
    ): SyncStatusRepository

    @Binds
    @Singleton
    abstract fun bindTransactionRepository(
        transactionRepositoryImpl: TransactionRepositoryImpl
    ): TransactionRepository

    @Binds
    @Singleton
    abstract fun bindAccountRepository(
        accountRepositoryImpl: AccountRepositoryImpl
    ): AccountRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        categoryRepositoryImpl: CategoryRepositoryImpl
    ): CategoryRepository

    companion object {
    
    @Provides
    @Singleton
    fun provideNetworkConnectivityObserver(context: Context): NetworkConnectivityObserver {
        return NetworkConnectivityObserverImpl(context)
    }
    
    @Provides
    @Singleton
    fun provideYandexFinanceDatabase(context: Context): YandexFinanceDatabase {
        return YandexFinanceDatabase.getInstance(context)
    }

    @Provides
    fun provideTransactionDao(database: YandexFinanceDatabase): TransactionDao {
        return database.transactionDao()
    }

    @Provides
    fun provideAccountDao(database: YandexFinanceDatabase): AccountDao {
        return database.accountDao()
    }

    @Provides
    fun provideCategoryDao(database: YandexFinanceDatabase): CategoryDao {
        return database.categoryDao()
    }

    @Provides
    fun provideSyncMetadataDao(database: YandexFinanceDatabase): SyncMetadataDao {
        return database.syncMetadataDao()
    }
    }
} 