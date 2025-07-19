package com.yandex.finance.core.data.repository

import com.yandex.finance.core.data.mapper.asExternalModel
import com.yandex.finance.core.data.mapper.asNetworkModel
import com.yandex.finance.core.data.observer.NetworkConnectivityObserver
import com.yandex.finance.core.data.sync.LocalIdGenerator
import com.yandex.finance.core.domain.model.transaction.CreatedTransaction
import com.yandex.finance.core.domain.model.transaction.Transaction
import com.yandex.finance.core.domain.model.transaction.TransactionWithoutId
import com.yandex.finance.core.localdb.dao.AccountDao
import com.yandex.finance.core.localdb.dao.CategoryDao
import com.yandex.finance.core.localdb.dao.TransactionDao
import com.yandex.finance.core.localdb.mapper.toBasicAccount
import com.yandex.finance.core.localdb.mapper.toDomainModel
import com.yandex.finance.core.localdb.mapper.toEntity
import com.yandex.finance.core.network.transaction.service.TransactionService
import kotlinx.datetime.Instant
import timber.log.Timber
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionService: TransactionService,
    private val transactionDao: TransactionDao,
    private val accountDao: AccountDao,
    private val categoryDao: CategoryDao,
    private val connectivityObserver: NetworkConnectivityObserver
) : TransactionRepository {

    override suspend fun createTransaction(body: TransactionWithoutId): Result<CreatedTransaction> {
        Timber.d("createTransaction was called. body: $body")
        return try {
            val isOnline = connectivityObserver.isConnected()
            
            if (isOnline) {
                try {
                    Timber.d("ONLINE: Creating transaction on server for account: ${body.accountId}")
                    val networkResult = transactionService.createTransaction(body.asNetworkModel())
                    
                    networkResult.fold(
                        onSuccess = { networkTransaction ->
                            // Save to local database with server ID
                            val entity = body.toEntity(
                                id = networkTransaction.id ?: 0,
                                isLocalOnly = false,
                                isPendingSync = false,
                                createdAt = networkTransaction.createdAt ?: "",
                                updatedAt = networkTransaction.updatedAt ?: ""
                            )
                            transactionDao.insertTransaction(entity)
                            Timber.d("ONLINE: Saved server transaction with ID: ${networkTransaction.id} for account: ${body.accountId}")
                            
                            Result.success(networkTransaction.asExternalModel())
                        },
                        onFailure = { error ->
                            Timber.w(error, "Failed to create transaction on server, saving locally")
                            createLocalTransaction(body)
                        }
                    )
                } catch (e: Exception) {
                    Timber.w(e, "Network error, saving transaction locally")
                    createLocalTransaction(body)
                }
            } else {
                Timber.d("OFFLINE: Creating local transaction for account: ${body.accountId}")
                createLocalTransaction(body)
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to create transaction")
            Result.failure(e)
        }
    }

    private suspend fun createLocalTransaction(body: TransactionWithoutId): Result<CreatedTransaction> {
        Timber.d("createLocalTransaction was called. body: $body")
        val localId = LocalIdGenerator.generateLocalId()
        val currentTime = Instant.fromEpochMilliseconds(System.currentTimeMillis()).toString()
        
        val entity = body.toEntity(
            id = localId,
            isLocalOnly = true,
            isPendingSync = true,
            createdAt = currentTime,
            updatedAt = currentTime
        )
        
        transactionDao.insertTransaction(entity)
        Timber.d("Created LOCAL transaction: $localId for account: ${body.accountId}")
        Timber.d(transactionDao.getTransactionById(localId).toString())
        
        val result = CreatedTransaction(
            id = localId,
            amount = body.amount,
            accountId = body.accountId,
            categoryId = body.categoryId,
            comment = body.comment,
            updatedAt = currentTime,
            createdAt = currentTime,
            transactionDate = body.transactionDate
        )
        
        return Result.success(result)
    }

    override suspend fun fetchTransactionById(id: Int): Result<Transaction> {
        return try {
            val localTransaction = transactionDao.getTransactionById(id)
            
            if (localTransaction != null) {
                val account = accountDao.getAccountById(localTransaction.accountId)?.toBasicAccount()
                val category = categoryDao.getCategoryById(localTransaction.categoryId)?.toDomainModel()
                
                if (account != null && category != null) {
                    val transaction = Transaction(
                        id = localTransaction.id,
                        amount = localTransaction.amount,
                        comment = localTransaction.comment,
                        account = account,
                        createdAt = localTransaction.createdAt,
                        updatedAt = localTransaction.updatedAt,
                        category = category,
                        transactionDate = localTransaction.transactionDate
                    )
                    return Result.success(transaction)
                }
            }
            
            if (connectivityObserver.isConnected() && !LocalIdGenerator.isLocalId(id)) {
                val networkResult = transactionService.fetchTransactionById(id)
                networkResult.fold(
                    onSuccess = { networkTransaction ->
                        // Save to local database
                        val entity = networkTransaction.asExternalModel().toEntity(
                            lastSyncAt = System.currentTimeMillis()
                        )
                        transactionDao.insertTransaction(entity)
                        Result.success(networkTransaction.asExternalModel())
                    },
                    onFailure = { error ->
                        Result.failure(error)
                    }
                )
            } else {
                Result.failure(Exception("Transaction not found"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to fetch transaction")
            Result.failure(e)
        }
    }

    override suspend fun updateTransaction(
        id: Int,
        body: TransactionWithoutId
    ): Result<Transaction> {
        Timber.d("updateTransaction was called. id: $id, body: $body")
        return try {
            val existingEntity = transactionDao.getTransactionById(id)
                ?: return Result.failure(Exception("Transaction not found"))
            
            val isOnline = connectivityObserver.isConnected()
            
            if (isOnline && !existingEntity.isLocalOnly) {
                try {
                    val networkResult = transactionService.updateTransaction(id, body.asNetworkModel())
                    networkResult.fold(
                        onSuccess = { networkTransaction ->
                            // Update local database
                            val updatedEntity = body.toEntity(
                                id = id,
                                isLocalOnly = false,
                                isPendingSync = false,
                                localCreatedAt = existingEntity.localCreatedAt,
                                createdAt = networkTransaction.createdAt ?: existingEntity.createdAt,
                                updatedAt = networkTransaction.updatedAt ?: Instant.fromEpochMilliseconds(System.currentTimeMillis()).toString()
                            )
                            transactionDao.updateTransaction(updatedEntity)
                            Result.success(networkTransaction.asExternalModel())
                        },
                        onFailure = { error ->
                            Timber.w(error, "Failed to update on server, marking for sync")
                            updateLocalTransaction(id, body, existingEntity)
                        }
                    )
                } catch (e: Exception) {
                    Timber.w(e, "Network error, updating locally")
                    updateLocalTransaction(id, body, existingEntity)
                }
            } else {
                updateLocalTransaction(id, body, existingEntity)
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to update transaction")
            Result.failure(e)
        }.also {
            Timber.d("updateTransaction completed. ${it.getOrNull()}")
        }
    }

    private suspend fun updateLocalTransaction(
        id: Int,
        body: TransactionWithoutId,
        existingEntity: com.yandex.finance.core.localdb.entity.TransactionEntity
    ): Result<Transaction> {
        val updatedEntity = body.toEntity(
            id = id,
            isLocalOnly = existingEntity.isLocalOnly,
            isPendingSync = true,
            localCreatedAt = existingEntity.localCreatedAt,
            createdAt = existingEntity.createdAt,
            updatedAt = Instant.fromEpochMilliseconds(System.currentTimeMillis()).toString()
        )
        
        transactionDao.updateTransaction(updatedEntity)
        
        val account = accountDao.getAccountById(body.accountId)?.toBasicAccount()
        val category = categoryDao.getCategoryById(body.categoryId)?.toDomainModel()
        
        if (account != null && category != null) {
            val transaction = Transaction(
                id = id,
                amount = body.amount,
                comment = body.comment,
                account = account,
                createdAt = updatedEntity.createdAt,
                updatedAt = updatedEntity.updatedAt,
                category = category,
                transactionDate = body.transactionDate
            )
            return Result.success(transaction)
        }
        
        return Result.failure(Exception("Failed to populate transaction details"))
    }

    override suspend fun deleteTransaction(id: Int): Result<Unit> {
        return try {
            val existingEntity = transactionDao.getTransactionById(id)
                ?: return Result.failure(Exception("Transaction not found"))
            
            val isOnline = connectivityObserver.isConnected()
            
            if (isOnline && !existingEntity.isLocalOnly) {
                try {
                    val networkResult = transactionService.deleteTransaction(id)
                    networkResult.fold(
                        onSuccess = {
                            transactionDao.deleteTransactionById(id)
                            Result.success(Unit)
                        },
                        onFailure = { error ->
                            Timber.w(error, "Failed to delete on server")
                            Result.failure(error)
                        }
                    )
                } catch (e: Exception) {
                    Timber.w(e, "Network error during deletion")
                    Result.failure(e)
                }
            } else {
                transactionDao.deleteTransactionById(id)
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to delete transaction")
            Result.failure(e)
        }
    }

    override suspend fun fetchTransactionsByPeriod(
        id: String,
        startDate: String?,
        endDate: String?
    ): Result<List<Transaction>> {
        Timber.d("fetchTransactionsByPeriod was called. id: $id, startDate: $startDate, endDate: $endDate")
        return try {
            val accountId = id.toIntOrNull() ?: return Result.failure(Exception("Invalid account ID"))
            
            if (connectivityObserver.isConnected()) {
                try {
                    val networkResult = transactionService.fetchTransactionsByPeriod(id, startDate, endDate)
                    networkResult.fold(
                        onSuccess = { networkTransactions ->
                            transactionDao.deleteTransactionsByPeriod(accountId, startDate, endDate)
                            
                            val entities = networkTransactions.map {
                                it.asExternalModel().toEntity(
                                    lastSyncAt = System.currentTimeMillis()
                                )
                            }
                            transactionDao.insertTransactions(entities)
                            
                            val serverDomainTransactions = networkTransactions.mapNotNull { networkTransaction ->
                                val domainTransaction = networkTransaction.asExternalModel()
                                val account = accountDao.getAccountById(domainTransaction.account.id)?.toBasicAccount()
                                val category = categoryDao.getCategoryById(domainTransaction.category.id)?.toDomainModel()
                                
                                if (account != null && category != null) {
                                    Transaction(
                                        id = domainTransaction.id,
                                        amount = domainTransaction.amount,
                                        comment = domainTransaction.comment,
                                        account = account,
                                        createdAt = domainTransaction.createdAt,
                                        updatedAt = domainTransaction.updatedAt,
                                        category = category,
                                        transactionDate = domainTransaction.transactionDate
                                    )
                                } else null
                            }
                            Result.success(serverDomainTransactions)
                        },
                        onFailure = { error ->
                            Timber.w(error, "Failed to fetch from server, falling back to local data")
                            fetchLocalTransactions(accountId, startDate, endDate)
                        }
                    )
                } catch (e: Exception) {
                    Timber.w(e, "Network error, falling back to local data")
                    fetchLocalTransactions(accountId, startDate, endDate)
                }
            } else {
                fetchLocalTransactions(accountId, startDate, endDate)
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to fetch transactions by period")
            Result.failure(e)
        }
    }

    private suspend fun fetchLocalTransactions(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): Result<List<Transaction>> {
        val localTransactions = transactionDao.getTransactionsByPeriod(accountId, startDate, endDate)
        Timber.d("localTransactions: ${localTransactions.joinToString { "$it" }}")
        Timber.d("AllLocal: ${transactionDao.getLocalOnlyTransactions()}")

        val domainTransactions = localTransactions.mapNotNull { entity ->
            val account = accountDao.getAccountById(entity.accountId)?.toBasicAccount()
            val category = categoryDao.getCategoryById(entity.categoryId)?.toDomainModel()
            
            if (account != null && category != null) {
                Transaction(
                    id = entity.id,
                    amount = entity.amount,
                    comment = entity.comment,
                    account = account,
                    createdAt = entity.createdAt,
                    updatedAt = entity.updatedAt,
                    category = category,
                    transactionDate = entity.transactionDate
                )
            } else null
        }
        
        return Result.success(domainTransactions)
    }
}
