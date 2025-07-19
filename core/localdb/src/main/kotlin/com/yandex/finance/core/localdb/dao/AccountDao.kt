package com.yandex.finance.core.localdb.dao

import androidx.room.*
import com.yandex.finance.core.localdb.entity.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Query("SELECT * FROM accounts ORDER BY localCreatedAt DESC")
    fun getAllAccounts(): Flow<List<AccountEntity>>

    @Query("SELECT * FROM accounts ORDER BY localCreatedAt DESC")
    suspend fun getAllAccountsSync(): List<AccountEntity>

    @Query("SELECT * FROM accounts WHERE id = :id")
    suspend fun getAccountById(id: Int): AccountEntity?

    @Query("SELECT * FROM accounts WHERE isPendingSync = 1")
    suspend fun getPendingSyncAccounts(): List<AccountEntity>

    @Query("SELECT * FROM accounts WHERE isLocalOnly = 1")
    suspend fun getLocalOnlyAccounts(): List<AccountEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: AccountEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccounts(accounts: List<AccountEntity>)

    @Update
    suspend fun updateAccount(account: AccountEntity)

    @Delete
    suspend fun deleteAccount(account: AccountEntity)

    @Query("DELETE FROM accounts WHERE id = :id")
    suspend fun deleteAccountById(id: Int)

    @Query("UPDATE accounts SET isPendingSync = 0, lastSyncAt = :syncTime WHERE id = :id")
    suspend fun markAsSynced(id: Int, syncTime: Long)

    @Query("UPDATE accounts SET isPendingSync = 1 WHERE id = :id")
    suspend fun markAsPendingSync(id: Int)

    @Query("DELETE FROM accounts")
    suspend fun clearAll()
} 