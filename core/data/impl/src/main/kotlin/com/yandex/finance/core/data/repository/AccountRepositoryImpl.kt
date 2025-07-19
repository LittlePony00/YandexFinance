package com.yandex.finance.core.data.repository

import com.yandex.finance.core.data.mapper.asExternalModel
import com.yandex.finance.core.data.mapper.asNetworkModel
import com.yandex.finance.core.data.observer.NetworkConnectivityObserver
import com.yandex.finance.core.data.sync.LocalIdGenerator
import com.yandex.finance.core.domain.model.account.AccountDetailed
import com.yandex.finance.core.domain.model.account.AccountHistory
import com.yandex.finance.core.domain.model.account.AccountWithoutId
import com.yandex.finance.core.domain.model.account.MainAccount
import com.yandex.finance.core.domain.model.account.NewState
import com.yandex.finance.core.domain.model.account.PreviousState
import com.yandex.finance.core.localdb.dao.AccountDao
import com.yandex.finance.core.localdb.mapper.toDetailedAccount
import com.yandex.finance.core.localdb.mapper.toEntity
import com.yandex.finance.core.network.account.service.AccountService
import timber.log.Timber
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountService: AccountService,
    private val accountDao: AccountDao,
    private val connectivityObserver: NetworkConnectivityObserver
) : AccountRepository {

    override suspend fun fetchAccounts(): Result<List<AccountDetailed>> {
        return try {
            val localAccounts = accountDao.getAllAccountsSync()
            
            if (connectivityObserver.isConnected()) {
                try {
                    val networkResult = accountService.fetchAccounts()
                    networkResult.fold(
                        onSuccess = { networkAccounts ->
                            val entities = networkAccounts.map {
                                it.asExternalModel().toEntity(
                                    lastSyncAt = System.currentTimeMillis()
                                )
                            }
                            accountDao.insertAccounts(entities)
                            
                            val domainAccounts = networkAccounts.map { it.asExternalModel() }
                            Result.success(domainAccounts)
                        },
                        onFailure = { error ->
                            Timber.w(error, "Failed to fetch from server, using local data")
                            val localDomainAccounts = localAccounts.map { it.toDetailedAccount() }
                            Result.success(localDomainAccounts)
                        }
                    )
                } catch (e: Exception) {
                    Timber.w(e, "Network error, using local data")
                    val localDomainAccounts = localAccounts.map { it.toDetailedAccount() }
                    Result.success(localDomainAccounts)
                }
            } else {
                val localDomainAccounts = localAccounts.map { it.toDetailedAccount() }
                Result.success(localDomainAccounts)
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to fetch accounts")
            Result.failure(e)
        }
    }

    override suspend fun createAccount(body: AccountWithoutId): Result<AccountDetailed> {
        return try {
            val isOnline = connectivityObserver.isConnected()
            
            if (isOnline) {
                try {
                    val networkResult = accountService.createAccount(body.asNetworkModel())
                    networkResult.fold(
                        onSuccess = { networkAccount ->
                            // Save to local database with server ID
                            val entity = networkAccount.asExternalModel().toEntity(
                                isLocalOnly = false,
                                isPendingSync = false,
                                lastSyncAt = System.currentTimeMillis()
                            )
                            accountDao.insertAccount(entity)
                            Result.success(networkAccount.asExternalModel())
                        },
                        onFailure = { error ->
                            Timber.w(error, "Failed to create account on server, saving locally")
                            createLocalAccount(body)
                        }
                    )
                } catch (e: Exception) {
                    Timber.w(e, "Network error, saving account locally")
                    createLocalAccount(body)
                }
            } else {
                createLocalAccount(body)
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to create account")
            Result.failure(e)
        }
    }

    private suspend fun createLocalAccount(body: AccountWithoutId): Result<AccountDetailed> {
        val localId = LocalIdGenerator.generateLocalId()
        val currentTime = System.currentTimeMillis().toString()
        
        val entity = body.toEntity(
            id = localId,
            isLocalOnly = true,
            isPendingSync = true,
            createdAt = currentTime,
            updatedAt = currentTime
        )
        
        accountDao.insertAccount(entity)
        
        val result = AccountDetailed(
            id = localId,
            userId = entity.userId ?: 0,
            name = body.name,
            balance = body.balance,
            currency = body.currency,
            createdAt = currentTime,
            updatedAt = currentTime
        )
        
        return Result.success(result)
    }

    override suspend fun fetchAccount(id: String): Result<MainAccount> {
        return try {
            val accountId = id.toIntOrNull() ?: return Result.failure(Exception("Invalid account ID"))
            
            val localAccount = accountDao.getAccountById(accountId)
            
            if (localAccount != null) {
                val mainAccount = MainAccount(
                    id = localAccount.id,
                    name = localAccount.name,
                    balance = localAccount.balance,
                    currency = localAccount.currency,
                    createdAt = localAccount.createdAt,
                    updatedAt = localAccount.updatedAt,
                    incomeStats = emptyList(),
                    expenseStats = emptyList()
                )
                return Result.success(mainAccount)
            }
            
            if (connectivityObserver.isConnected()) {
                val networkResult = accountService.fetchAccount(id)
                networkResult.fold(
                    onSuccess = { networkAccount ->
                        // Save to local database
                        val entity = networkAccount.asExternalModel().toEntity(
                            isLocalOnly = false,
                            isPendingSync = false,
                            lastSyncAt = System.currentTimeMillis()
                        )
                        accountDao.insertAccount(entity)
                        Result.success(networkAccount.asExternalModel())
                    },
                    onFailure = { error ->
                        Result.failure(error)
                    }
                )
            } else {
                Result.failure(Exception("Account not found"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to fetch account")
            Result.failure(e)
        }
    }

    override suspend fun updateAccount(
        id: String,
        body: AccountWithoutId
    ): Result<AccountDetailed> {
        return try {
            val accountId = id.toIntOrNull() ?: return Result.failure(Exception("Invalid account ID"))
            
            val existingEntity = accountDao.getAccountById(accountId)
                ?: return Result.failure(Exception("Account not found"))
            
            val isOnline = connectivityObserver.isConnected()
            
            if (isOnline && !existingEntity.isLocalOnly) {
                try {
                    val networkResult = accountService.updateAccount(id, body.asNetworkModel())
                    networkResult.fold(
                        onSuccess = { networkAccount ->
                            // Update local database
                            val updatedEntity = body.toEntity(
                                id = accountId,
                                isLocalOnly = false,
                                isPendingSync = false,
                                localCreatedAt = existingEntity.localCreatedAt,
                                createdAt = networkAccount.createdAt ?: existingEntity.createdAt,
                                updatedAt = networkAccount.updatedAt ?: System.currentTimeMillis().toString()
                            )
                            accountDao.updateAccount(updatedEntity)
                            Result.success(networkAccount.asExternalModel())
                        },
                        onFailure = { error ->
                            Timber.w(error, "Failed to update on server, marking for sync")
                            updateLocalAccount(accountId, body, existingEntity)
                        }
                    )
                } catch (e: Exception) {
                    Timber.w(e, "Network error, updating locally")
                    updateLocalAccount(accountId, body, existingEntity)
                }
            } else {
                updateLocalAccount(accountId, body, existingEntity)
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to update account")
            Result.failure(e)
        }
    }

    private suspend fun updateLocalAccount(
        id: Int,
        body: AccountWithoutId,
        existingEntity: com.yandex.finance.core.localdb.entity.AccountEntity
    ): Result<AccountDetailed> {
        val updatedEntity = body.toEntity(
            id = id,
            isLocalOnly = existingEntity.isLocalOnly,
            isPendingSync = true,
            localCreatedAt = existingEntity.localCreatedAt,
            createdAt = existingEntity.createdAt,
            updatedAt = System.currentTimeMillis().toString()
        )
        
        accountDao.updateAccount(updatedEntity)
        
        val result = AccountDetailed(
            id = id,
            userId = updatedEntity.userId ?: 0,
            name = body.name,
            balance = body.balance,
            currency = body.currency,
            createdAt = updatedEntity.createdAt,
            updatedAt = updatedEntity.updatedAt
        )
        
        return Result.success(result)
    }

    override suspend fun deleteAccount(id: String): Result<Unit> {
        return try {
            val accountId = id.toIntOrNull() ?: return Result.failure(Exception("Invalid account ID"))
            
            val existingEntity = accountDao.getAccountById(accountId)
                ?: return Result.failure(Exception("Account not found"))
            
            val isOnline = connectivityObserver.isConnected()
            
            if (isOnline && !existingEntity.isLocalOnly) {
                try {
                    val networkResult = accountService.deleteAccount(id)
                    networkResult.fold(
                        onSuccess = {
                            accountDao.deleteAccountById(accountId)
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
                accountDao.deleteAccountById(accountId)
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to delete account")
            Result.failure(e)
        }
    }

    override suspend fun fetchAccountHistory(id: String): Result<AccountHistory> {
        return try {
            if (connectivityObserver.isConnected()) {
                val networkResult = accountService.fetchAccountHistory(id)
                networkResult.fold(
                    onSuccess = { networkHistory ->
                        Result.success(networkHistory.asExternalModel())
                    },
                    onFailure = { error ->
                        Timber.w(error, "Failed to fetch account history from server")
                        val accountIdInt = id.toIntOrNull() ?: 0
                        val emptyState = NewState(accountIdInt, "", "0", "")
                        val emptyPreviousState = PreviousState(accountIdInt, "", "0", "")
                        Result.success(AccountHistory(
                            id = 0,
                            accountId = accountIdInt,
                            createdAt = System.currentTimeMillis().toString(),
                            changeType = "NO_CHANGE",
                            newState = emptyState,
                            changeTimestamp = System.currentTimeMillis().toString(),
                            previousState = emptyPreviousState
                        ))
                    }
                )
            } else {
                val accountIdInt = id.toIntOrNull() ?: 0
                val emptyState = NewState(accountIdInt, "", "0", "")
                val emptyPreviousState = PreviousState(accountIdInt, "", "0", "")
                Result.success(AccountHistory(
                    id = 0,
                    accountId = accountIdInt,
                    createdAt = System.currentTimeMillis().toString(),
                    changeType = "NO_CHANGE",
                    newState = emptyState,
                    changeTimestamp = System.currentTimeMillis().toString(),
                    previousState = emptyPreviousState
                ))
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to fetch account history")
            Result.failure(e)
        }
    }
}
