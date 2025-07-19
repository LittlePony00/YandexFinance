package com.yandex.finance.core.data.sync

import android.content.Context
import androidx.work.*
import com.yandex.finance.core.data.observer.NetworkConnectivityObserver
import com.yandex.finance.core.data.mapper.asExternalModel
import com.yandex.finance.core.data.mapper.asNetworkModel
import com.yandex.finance.core.localdb.dao.*
import com.yandex.finance.core.localdb.entity.*
import com.yandex.finance.core.localdb.mapper.*
import com.yandex.finance.core.network.account.service.AccountService
import com.yandex.finance.core.network.category.service.CategoryService
import com.yandex.finance.core.network.transaction.service.TransactionService
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataSyncService @Inject constructor(
    private val transactionDao: TransactionDao,
    private val accountDao: AccountDao,
    private val categoryDao: CategoryDao,
    private val syncMetadataDao: SyncMetadataDao,
    private val transactionService: TransactionService,
    private val accountService: AccountService,
    private val categoryService: CategoryService,
    private val connectivityObserver: NetworkConnectivityObserver,
    private val context: Context
) {
    private val transactionMutexes = mutableMapOf<Int, Mutex>()

    suspend fun syncAllData(): List<SyncResult> {
        if (!connectivityObserver.isConnected()) {
            Timber.w("No network connection, skipping sync")
            return emptyList()
        }

        val results = mutableListOf<SyncResult>()
        
        results.add(syncCategories())
        results.add(syncAccounts())
        results.add(syncTransactions())
        
        return results
    }

    suspend fun triggerManualSync(): Boolean {
        return try {
            if (!connectivityObserver.isConnected()) {
                Timber.w("Cannot trigger manual sync: No network connection")
                return false
            }
            
            Timber.d("Triggering manual sync")
            val syncResults = syncAllData()
            val allSuccessful = syncResults.all { it.success }
            
            if (allSuccessful) {
                Timber.d("Manual sync completed successfully")
            } else {
                Timber.w("Manual sync completed with errors")
            }
            
            allSuccessful
        } catch (e: Exception) {
            Timber.e(e, "Manual sync failed")
            false
        }
    }

    suspend fun schedulePeriodicSync() {
        try {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build()
            
            val periodicSyncRequest = PeriodicWorkRequestBuilder<SyncWorker>(
                2, TimeUnit.HOURS
            )
                .setConstraints(constraints)
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    15, TimeUnit.MINUTES
                )
                .addTag("periodic_sync")
                .build()
            
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "periodic_sync_work",
                ExistingPeriodicWorkPolicy.KEEP,
                periodicSyncRequest
            )
            
            Timber.d("Scheduled periodic sync every 2 hours")
        } catch (e: Exception) {
            Timber.e(e, "Failed to schedule periodic sync")
        }
    }

    suspend fun triggerImmediateSync() {
        try {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            
            val inputData = workDataOf("one_time_sync" to true)
            
            val oneTimeSyncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
                .setConstraints(constraints)
                .setInputData(inputData)
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    10, TimeUnit.SECONDS
                )
                .addTag("one_time_sync")
                .build()
            
            WorkManager.getInstance(context).enqueueUniqueWork(
                "one_time_sync_work",
                ExistingWorkPolicy.REPLACE,
                oneTimeSyncRequest
            )
            
            Timber.d("Triggered immediate sync")
        } catch (e: Exception) {
            Timber.e(e, "Failed to trigger immediate sync")
        }
    }

    suspend fun getLastSyncTime(dataType: DataType): Long? {
        return syncMetadataDao.getLastSyncTime(dataType.value)
    }

    private suspend fun syncCategories(): SyncResult {
        return withContext(Dispatchers.IO) {
            try {
                Timber.d("Starting category sync")
                markSyncInProgress(DataType.CATEGORIES)
                
                var syncedItems = 0
                
                val categoriesResult = categoryService.fetchCategories()
                
                categoriesResult.fold(
                    onSuccess = { networkCategories ->
                        val entities = networkCategories.map { 
                            it.asExternalModel().toEntity(
                                lastSyncAt = System.currentTimeMillis()
                            )
                        }
                        categoryDao.insertCategories(entities)
                        syncedItems = entities.size
                        Timber.d("Synced ${syncedItems} categories")
                    },
                    onFailure = { error ->
                        Timber.w(error, "Failed to fetch categories from server")
                        throw error
                    }
                )
                
                markSyncSuccess(DataType.CATEGORIES)
                
                SyncResult(
                    success = true,
                    dataType = DataType.CATEGORIES,
                    syncedItems = syncedItems,
                    conflictedItems = 0
                )
                
            } catch (e: Exception) {
                Timber.e(e, "Category sync failed")
                markSyncFailed(DataType.CATEGORIES, e.message ?: "Unknown error")
                
                SyncResult(
                    success = false,
                    dataType = DataType.CATEGORIES,
                    errorMessage = e.message
                )
            }
        }
    }

    private suspend fun syncAccounts(): SyncResult {
        return withContext(Dispatchers.IO) {
            try {
                Timber.d("Starting account sync")
                markSyncInProgress(DataType.ACCOUNTS)
                
                var syncedItems = 0
                
                val pendingAccounts = accountDao.getPendingSyncAccounts()
                for (account in pendingAccounts) {
                    syncAccountToServer(account)
                    syncedItems++
                }
                
                val accountsResult = accountService.fetchAccounts()
                accountsResult.fold(
                    onSuccess = { networkAccounts ->
                        val entities = networkAccounts.map { 
                            it.asExternalModel().toEntity(
                                isLocalOnly = false,
                                isPendingSync = false,
                                lastSyncAt = System.currentTimeMillis()
                            )
                        }
                        accountDao.insertAccounts(entities)
                        syncedItems += entities.size
                    },
                    onFailure = { error ->
                        Timber.w(error, "Failed to fetch accounts from server")
                    }
                )
                
                markSyncSuccess(DataType.ACCOUNTS)
                
                SyncResult(
                    success = true,
                    dataType = DataType.ACCOUNTS,
                    syncedItems = syncedItems,
                    conflictedItems = 0
                )
                
            } catch (e: Exception) {
                Timber.e(e, "Account sync failed")
                markSyncFailed(DataType.ACCOUNTS, e.message ?: "Unknown error")
                
                SyncResult(
                    success = false,
                    dataType = DataType.ACCOUNTS,
                    errorMessage = e.message
                )
            }
        }
    }

    private suspend fun syncTransactions(): SyncResult {
        return withContext(Dispatchers.IO) {
            try {
                Timber.d("Starting transaction sync")
                markSyncInProgress(DataType.TRANSACTIONS)
                
                var syncedItems = 0
                
                val localOnlyTransactions = transactionDao.getLocalOnlyTransactions()
                for (transaction in localOnlyTransactions) {
                    syncTransactionToServer(transaction)
                    syncedItems++
                }
                
                markSyncSuccess(DataType.TRANSACTIONS)
                
                SyncResult(
                    success = true,
                    dataType = DataType.TRANSACTIONS,
                    syncedItems = syncedItems,
                    conflictedItems = 0
                )
                
            } catch (e: Exception) {
                Timber.e(e, "Transaction sync failed")
                markSyncFailed(DataType.TRANSACTIONS, e.message ?: "Unknown error")
                
                SyncResult(
                    success = false,
                    dataType = DataType.TRANSACTIONS,
                    errorMessage = e.message
                )
            }
        }
    }

    private suspend fun syncTransactionToServer(transaction: TransactionEntity) {
        try {
            if (LocalIdGenerator.isLocalId(transaction.id)) {
                val mutex = transactionMutexes.getOrPut(transaction.id) { Mutex() }
                
                mutex.withLock {
                    val currentTransaction = transactionDao.getTransactionById(transaction.id)
                    if (currentTransaction == null || !currentTransaction.isLocalOnly) {
                        Timber.d("Transaction ${transaction.id} already synced or deleted by another thread, skipping")
                        return@withLock
                    }
                    
                    val transactionWithoutId = transaction.toTransactionWithoutId()
                    val result = transactionService.createTransaction(transactionWithoutId.asNetworkModel())
                
                result.fold(
                    onSuccess = { createdTransaction ->
                        transactionDao.deleteTransactionById(transaction.id)
                        
                        val newEntity = transaction.copy(
                            id = createdTransaction.id ?: transaction.id,
                            createdAt = createdTransaction.createdAt ?: transaction.createdAt,
                            updatedAt = createdTransaction.updatedAt ?: transaction.updatedAt,
                            isLocalOnly = false,
                            isPendingSync = false,
                            lastSyncAt = System.currentTimeMillis()
                        )
                        transactionDao.insertTransaction(newEntity)
                        Timber.d("Created transaction on server: ${transaction.id} -> ${createdTransaction.id}")
                    },
                    onFailure = { error ->
                        Timber.w(error, "Failed to create transaction ${transaction.id} on server")
                    }
                )
                }
            } else {
                val transactionWithoutId = transaction.toTransactionWithoutId()
                val result = transactionService.updateTransaction(transaction.id, transactionWithoutId.asNetworkModel())
                
                result.fold(
                    onSuccess = { _ ->
                        transactionDao.markAsSynced(transaction.id, System.currentTimeMillis())
                        Timber.d("Updated transaction on server: ${transaction.id}")
                    },
                    onFailure = { error ->
                        Timber.w(error, "Failed to update transaction ${transaction.id} on server")
                    }
                )
            }
        } catch (e: Exception) {
            Timber.e(e, "Error syncing transaction ${transaction.id}")
        }
    }

    private suspend fun syncAccountToServer(account: AccountEntity) {
        try {
            if (LocalIdGenerator.isLocalId(account.id)) {
                val accountWithoutId = account.toAccountWithoutId()
                val result = accountService.createAccount(accountWithoutId.asNetworkModel())
                
                result.fold(
                    onSuccess = { createdAccount ->
                        // Replace local account with server version
                        accountDao.deleteAccountById(account.id)
                        
                        val newEntity = createdAccount.asExternalModel().toEntity(
                            isLocalOnly = false,
                            isPendingSync = false,
                            localCreatedAt = account.localCreatedAt,
                            lastSyncAt = System.currentTimeMillis()
                        )
                        accountDao.insertAccount(newEntity)
                        Timber.d("Created account on server: ${account.id} -> ${createdAccount.id}")
                    },
                    onFailure = { error ->
                        Timber.w(error, "Failed to create account ${account.id} on server")
                    }
                )
            } else {
                val accountWithoutId = account.toAccountWithoutId()
                val result = accountService.updateAccount(account.id.toString(), accountWithoutId.asNetworkModel())
                
                result.fold(
                    onSuccess = { _ ->
                        accountDao.markAsSynced(account.id, System.currentTimeMillis())
                        Timber.d("Updated account on server: ${account.id}")
                    },
                    onFailure = { error ->
                        Timber.w(error, "Failed to update account ${account.id} on server")
                    }
                )
            }
        } catch (e: Exception) {
            Timber.e(e, "Error syncing account ${account.id}")
        }
    }

    private suspend fun markSyncInProgress(dataType: DataType) {
        val timestamp = System.currentTimeMillis()
        syncMetadataDao.markSyncInProgress(dataType.value, timestamp)
    }

    private suspend fun markSyncSuccess(dataType: DataType) {
        val timestamp = System.currentTimeMillis()
        syncMetadataDao.markSyncSuccess(dataType.value, timestamp)
    }

    private suspend fun markSyncFailed(dataType: DataType, errorMessage: String) {
        val timestamp = System.currentTimeMillis()
        syncMetadataDao.markSyncFailed(dataType.value, timestamp, errorMessage)
    }
}

private fun TransactionEntity.toTransactionWithoutId(): com.yandex.finance.core.domain.model.transaction.TransactionWithoutId {
    return com.yandex.finance.core.domain.model.transaction.TransactionWithoutId(
        accountId = accountId,
        amount = amount,
        categoryId = categoryId,
        comment = comment ?: "",
        transactionDate = transactionDate
    )
}

private fun AccountEntity.toAccountWithoutId(): com.yandex.finance.core.domain.model.account.AccountWithoutId {
    return com.yandex.finance.core.domain.model.account.AccountWithoutId(
        name = name,
        balance = balance,
        currency = currency
    )
} 