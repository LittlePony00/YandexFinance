package com.yandex.finance.core.localdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sync_metadata")
data class SyncMetadataEntity(
    @PrimaryKey
    val dataType: String, // "transactions", "accounts", "categories"
    val lastSuccessfulSync: Long, // Timestamp of last successful sync
    val lastSyncAttempt: Long, // Timestamp of last sync attempt (success or failure)
    val syncStatus: String, // "SUCCESS", "FAILED", "IN_PROGRESS"
    val errorMessage: String? = null, // Error message if sync failed
    val pendingChanges: Int = 0 // Number of pending changes to sync
) 