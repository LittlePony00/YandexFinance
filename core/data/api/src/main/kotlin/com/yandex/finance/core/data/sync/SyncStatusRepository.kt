package com.yandex.finance.core.data.sync

/**
 * Repository interface for sync status operations
 */
interface SyncStatusRepository {
    
    /**
     * Get formatted last sync time for display in UI
     */
    suspend fun getFormattedLastSyncTime(): String
    
    /**
     * Get detailed sync information for display
     */
    suspend fun getSyncInfo(): SyncInfo
    
    /**
     * Trigger manual sync
     */
    suspend fun triggerManualSync(): Boolean
}

/**
 * Data class for comprehensive sync information
 */
data class SyncInfo(
    val overallStatus: SyncStatus,
    val lastSyncTime: Long?,
    val formattedLastSyncTime: String
)

enum class SyncStatus {
    SUCCESS,
    FAILED,
    IN_PROGRESS,
    PENDING
}

enum class DataType(val value: String) {
    TRANSACTIONS("transactions"),
    ACCOUNTS("accounts"),
    CATEGORIES("categories")
}

data class SyncResult(
    val success: Boolean,
    val dataType: DataType,
    val syncedItems: Int = 0,
    val conflictedItems: Int = 0,
    val errorMessage: String? = null,
    val timestamp: Long = System.currentTimeMillis()
) 