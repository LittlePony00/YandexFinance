package com.yandex.finance.core.localdb.dao

import androidx.room.*
import com.yandex.finance.core.localdb.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions ORDER BY transactionDate DESC, localCreatedAt DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getTransactionById(id: Int): TransactionEntity?

    @Query("SELECT * FROM transactions WHERE accountId = :accountId AND (:startDate IS NULL OR transactionDate >= :startDate) AND (:endDate IS NULL OR transactionDate < :endDate || 'T23:59:59.999Z') ORDER BY transactionDate DESC")
    suspend fun getTransactionsByPeriod(
        accountId: Int,
        startDate: String? = null,
        endDate: String? = null
    ): List<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE isPendingSync = 1")
    suspend fun getPendingSyncTransactions(): List<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE isLocalOnly = 1")
    suspend fun getLocalOnlyTransactions(): List<TransactionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactions(transactions: List<TransactionEntity>)

    @Update
    suspend fun updateTransaction(transaction: TransactionEntity)

    @Delete
    suspend fun deleteTransaction(transaction: TransactionEntity)

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteTransactionById(id: Int)

    @Query("DELETE FROM transactions WHERE accountId = :accountId AND (:startDate IS NULL OR transactionDate >= :startDate) AND (:endDate IS NULL OR transactionDate < :endDate || 'T23:59:59.999Z')")
    suspend fun deleteTransactionsByPeriod(
        accountId: Int,
        startDate: String? = null,
        endDate: String? = null
    )

    @Query("UPDATE transactions SET isPendingSync = 0, lastSyncAt = :syncTime WHERE id = :id")
    suspend fun markAsSynced(id: Int, syncTime: Long)

    @Query("UPDATE transactions SET isPendingSync = 1 WHERE id = :id")
    suspend fun markAsPendingSync(id: Int)

    @Query("DELETE FROM transactions")
    suspend fun clearAll()
} 