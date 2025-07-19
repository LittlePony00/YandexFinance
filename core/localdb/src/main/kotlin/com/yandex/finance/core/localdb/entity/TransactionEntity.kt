package com.yandex.finance.core.localdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey
    val id: Int,
    val amount: String,
    val comment: String?,
    val accountId: Int,
    val categoryId: Int,
    val transactionDate: String,
    val createdAt: String,
    val updatedAt: String,
    
    // Sync metadata
    val isLocalOnly: Boolean = false, // True if created offline and not yet synced
    val isPendingSync: Boolean = false, // True if needs to be synced to server
    val localCreatedAt: Long = System.currentTimeMillis(), // Local creation timestamp
    val lastSyncAt: Long? = null, // Last successful sync timestamp
    val syncVersion: Int = 1 // Version for conflict resolution
) 