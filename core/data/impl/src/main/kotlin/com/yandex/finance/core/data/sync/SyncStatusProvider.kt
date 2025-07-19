package com.yandex.finance.core.data.sync

import com.yandex.finance.core.localdb.dao.SyncMetadataDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncStatusProvider @Inject constructor(
    private val syncMetadataDao: SyncMetadataDao
) {

    /**
     * Get the last sync time for a specific data type
     */
    suspend fun getLastSyncTime(dataType: DataType): Long? {
        return try {
            syncMetadataDao.getLastSyncTime(dataType.value)
        } catch (e: Exception) {
            Timber.e(e, "Failed to get last sync time for ${dataType.value}")
            null
        }
    }

    /**
     * Get the overall last sync time (most recent across all data types)
     */
    suspend fun getOverallLastSyncTime(): Long? {
        return try {
            val transactionSync = syncMetadataDao.getLastSyncTime(DataType.TRANSACTIONS.value) ?: 0
            val accountSync = syncMetadataDao.getLastSyncTime(DataType.ACCOUNTS.value) ?: 0
            val categorySync = syncMetadataDao.getLastSyncTime(DataType.CATEGORIES.value) ?: 0
            
            val maxSync = maxOf(transactionSync, accountSync, categorySync)
            if (maxSync > 0) maxSync else null
        } catch (e: Exception) {
            Timber.e(e, "Failed to get overall last sync time")
            null
        }
    }

    /**
     * Get formatted last sync time for display in UI
     */
    suspend fun getFormattedLastSyncTime(dataType: DataType): String {
        val lastSync = getLastSyncTime(dataType)
        return formatSyncTime(lastSync)
    }

    /**
     * Get formatted overall last sync time for display in UI
     */
    suspend fun getFormattedOverallLastSyncTime(): String {
        val lastSync = getOverallLastSyncTime()
        return formatSyncTime(lastSync)
    }

    /**
     * Get sync status for all data types
     */
    suspend fun getAllSyncStatuses(): Map<DataType, SyncStatus> {
        return try {
            mapOf(
                DataType.TRANSACTIONS to getSyncStatus(DataType.TRANSACTIONS),
                DataType.ACCOUNTS to getSyncStatus(DataType.ACCOUNTS),
                DataType.CATEGORIES to getSyncStatus(DataType.CATEGORIES)
            )
        } catch (e: Exception) {
            Timber.e(e, "Failed to get all sync statuses")
            emptyMap()
        }
    }

    /**
     * Get sync status for a specific data type
     */
    suspend fun getSyncStatus(dataType: DataType): SyncStatus {
        return try {
            val metadata = syncMetadataDao.getSyncMetadata(dataType.value)
            when (metadata?.syncStatus) {
                "SUCCESS" -> SyncStatus.SUCCESS
                "FAILED" -> SyncStatus.FAILED
                "IN_PROGRESS" -> SyncStatus.IN_PROGRESS
                else -> SyncStatus.PENDING
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to get sync status for ${dataType.value}")
            SyncStatus.PENDING
        }
    }

    /**
     * Get detailed sync information for display
     */
    suspend fun getDetailedSyncInfo(): DetailedSyncInfo {
        return try {
            val transactionStatus = getSyncStatus(DataType.TRANSACTIONS)
            val accountStatus = getSyncStatus(DataType.ACCOUNTS)
            val categoryStatus = getSyncStatus(DataType.CATEGORIES)
            
            val overallLastSync = getOverallLastSyncTime()
            val formattedTime = formatSyncTime(overallLastSync)
            
            val isInProgress = listOf(transactionStatus, accountStatus, categoryStatus)
                .any { it == SyncStatus.IN_PROGRESS }
            
            val hasFailed = listOf(transactionStatus, accountStatus, categoryStatus)
                .any { it == SyncStatus.FAILED }
            
            val overallStatus = when {
                isInProgress -> SyncStatus.IN_PROGRESS
                hasFailed -> SyncStatus.FAILED
                overallLastSync != null -> SyncStatus.SUCCESS
                else -> SyncStatus.PENDING
            }
            
            DetailedSyncInfo(
                overallStatus = overallStatus,
                lastSyncTime = overallLastSync,
                formattedLastSyncTime = formattedTime,
                transactionStatus = transactionStatus,
                accountStatus = accountStatus,
                categoryStatus = categoryStatus
            )
        } catch (e: Exception) {
            Timber.e(e, "Failed to get detailed sync info")
            DetailedSyncInfo(
                overallStatus = SyncStatus.FAILED,
                lastSyncTime = null,
                formattedLastSyncTime = "Never",
                transactionStatus = SyncStatus.FAILED,
                accountStatus = SyncStatus.FAILED,
                categoryStatus = SyncStatus.FAILED
            )
        }
    }

    /**
     * Get a Flow of sync metadata for reactive UI updates
     */
    fun getSyncMetadataFlow(): Flow<List<SyncMetadataInfo>> {
        return syncMetadataDao.getAllSyncMetadata().map { metadataList ->
            metadataList.map { metadata ->
                SyncMetadataInfo(
                    dataType = when (metadata.dataType) {
                        DataType.TRANSACTIONS.value -> DataType.TRANSACTIONS
                        DataType.ACCOUNTS.value -> DataType.ACCOUNTS
                        DataType.CATEGORIES.value -> DataType.CATEGORIES
                        else -> DataType.TRANSACTIONS // fallback
                    },
                    status = when (metadata.syncStatus) {
                        "SUCCESS" -> SyncStatus.SUCCESS
                        "FAILED" -> SyncStatus.FAILED
                        "IN_PROGRESS" -> SyncStatus.IN_PROGRESS
                        else -> SyncStatus.PENDING
                    },
                    lastSuccessfulSync = metadata.lastSuccessfulSync,
                    lastSyncAttempt = metadata.lastSyncAttempt,
                    errorMessage = metadata.errorMessage,
                    pendingChanges = metadata.pendingChanges,
                    formattedLastSync = formatSyncTime(metadata.lastSuccessfulSync)
                )
            }
        }
    }

    private fun formatSyncTime(timestamp: Long?): String {
        if (timestamp == null || timestamp == 0L) {
            return "Never"
        }
        
        val now = System.currentTimeMillis()
        val diff = now - timestamp
        
        return when {
            diff < 60_000 -> "Just now" // Less than 1 minute
            diff < 3600_000 -> "${diff / 60_000} minutes ago" // Less than 1 hour
            diff < 86400_000 -> "${diff / 3600_000} hours ago" // Less than 1 day
            diff < 604800_000 -> "${diff / 86400_000} days ago" // Less than 1 week
            else -> {
                // More than 1 week, show actual date
                val dateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
                dateFormat.format(Date(timestamp))
            }
        }
    }
}

/**
 * Data class for comprehensive sync information
 */
data class DetailedSyncInfo(
    val overallStatus: SyncStatus,
    val lastSyncTime: Long?,
    val formattedLastSyncTime: String,
    val transactionStatus: SyncStatus,
    val accountStatus: SyncStatus,
    val categoryStatus: SyncStatus
)

/**
 * Data class for detailed sync metadata information
 */
data class SyncMetadataInfo(
    val dataType: DataType,
    val status: SyncStatus,
    val lastSuccessfulSync: Long,
    val lastSyncAttempt: Long,
    val errorMessage: String?,
    val pendingChanges: Int,
    val formattedLastSync: String
) 