package com.yandex.finance.core.data.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class SyncWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            Timber.d("Starting background sync work")

            val dataSyncService = DataSyncServiceHolder.instance
                ?: return@withContext Result.failure()
            
            val syncResults = dataSyncService.syncAllData()
            
            if (syncResults.isEmpty()) {
                Timber.w("No sync performed (likely offline)")
                return@withContext Result.retry()
            }
            
            val allSuccessful = syncResults.all { it.success }
            val hasErrors = syncResults.any { !it.success }
            
            if (allSuccessful) {
                Timber.d("Background sync completed successfully")
                
                val outputData = workDataOf(
                    "sync_success" to true,
                    "synced_transactions" to (syncResults.firstOrNull { it.dataType == DataType.TRANSACTIONS }?.syncedItems ?: 0),
                    "synced_accounts" to (syncResults.firstOrNull { it.dataType == DataType.ACCOUNTS }?.syncedItems ?: 0),
                    "synced_categories" to (syncResults.firstOrNull { it.dataType == DataType.CATEGORIES }?.syncedItems ?: 0),
                    "total_conflicts" to syncResults.sumOf { it.conflictedItems },
                    "sync_timestamp" to System.currentTimeMillis()
                )
                
                Result.success(outputData)
            } else if (hasErrors) {
                Timber.w("Background sync completed with errors")
                
                val errorMessages = syncResults
                    .filter { !it.success }
                    .mapNotNull { it.errorMessage }
                    .joinToString("; ")
                
                val outputData = workDataOf(
                    "sync_success" to false,
                    "error_message" to errorMessages,
                    "sync_timestamp" to System.currentTimeMillis()
                )
                
                if (isRetryableError(errorMessages)) {
                    Result.retry()
                } else {
                    Result.failure(outputData)
                }
            } else {
                Timber.d("Background sync completed with mixed results")
                Result.success()
            }
            
        } catch (e: Exception) {
            Timber.e(e, "Background sync work failed")
            
            val outputData = workDataOf(
                "sync_success" to false,
                "error_message" to (e.message ?: "Unknown error"),
                "sync_timestamp" to System.currentTimeMillis()
            )
            
            if (isRetryableError(e.message)) {
                Result.retry()
            } else {
                Result.failure(outputData)
            }
        }
    }

    private fun isRetryableError(errorMessage: String?): Boolean {
        if (errorMessage == null) return false
        
        val retryablePatterns = listOf(
            "network",
            "timeout",
            "connection",
            "unavailable",
            "temporary"
        )
        
        return retryablePatterns.any { pattern ->
            errorMessage.lowercase().contains(pattern)
        }
    }
}

// Simple holder for DataSyncService access from WorkManager
object DataSyncServiceHolder {
    var instance: DataSyncService? = null
} 