package com.yandex.finance.core.data.sync

import android.content.Context
import com.yandex.finance.core.data.observer.NetworkConnectivityObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Simple offline manager for initializing and managing offline functionality
 * 
 * Usage in Application class:
 * ```
 * class YandexFinanceApplication : Application() {
 *     @Inject lateinit var offlineManager: OfflineManager
 *     
 *     override fun onCreate() {
 *         super.onCreate()
 *         // ... DI setup
 *         offlineManager.initialize()
 *     }
 * }
 * ```
 */
@Singleton
class OfflineManager @Inject constructor(
    private val context: Context,
    private val dataSyncService: DataSyncService,
    private val syncStatusProvider: SyncStatusProvider,
    private val connectivityObserver: NetworkConnectivityObserver
) {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var wasOffline = false

    /**
     * Initialize offline functionality
     * Call this during application startup
     */
    fun initialize() {
        try {
            Timber.d("Initializing offline functionality")
            
            // 0. Set the DataSyncService instance for WorkManager access
            DataSyncServiceHolder.instance = dataSyncService
            
            // 1. Schedule periodic background sync
            schedulePeriodicSync()
            
            // 2. Start network connectivity monitoring
            startNetworkMonitoring()
            
            // 3. Log current sync status
            logCurrentSyncStatus()
            
            Timber.d("Offline functionality initialization completed successfully")
        } catch (e: Exception) {
            Timber.e(e, "Failed to initialize offline functionality")
        }
    }

    /**
     * Trigger immediate sync manually
     * Useful for pull-to-refresh or manual sync buttons in UI
     */
    suspend fun triggerManualSync(): Boolean {
        return dataSyncService.triggerManualSync()
    }

    /**
     * Get formatted last sync time for display in UI
     */
    suspend fun getLastSyncTime(): String {
        return syncStatusProvider.getFormattedOverallLastSyncTime()
    }

    /**
     * Get detailed sync info for display
     */
    suspend fun getSyncInfo(): DetailedSyncInfo {
        return syncStatusProvider.getDetailedSyncInfo()
    }

    private fun schedulePeriodicSync() {
        try {
            applicationScope.launch {
                dataSyncService.schedulePeriodicSync()
            }
            Timber.d("Periodic sync scheduled successfully")
        } catch (e: Exception) {
            Timber.e(e, "Failed to schedule periodic sync")
        }
    }

    private fun startNetworkMonitoring() {
        try {
            applicationScope.launch {
                connectivityObserver.isConnected
                    .distinctUntilChanged()
                    .collect { isConnected ->
                        handleConnectivityChange(isConnected)
                    }
            }
            Timber.d("Network monitoring started successfully")
        } catch (e: Exception) {
            Timber.e(e, "Failed to start network monitoring")
        }
    }

    private suspend fun handleConnectivityChange(isConnected: Boolean) {
        when {
            !isConnected -> {
                // Connection lost
                wasOffline = true
                Timber.d("Network connection lost, sync will be triggered when connection is restored")
            }
            isConnected && wasOffline -> {
                // Connection restored after being offline
                wasOffline = false
                Timber.d("Network connection restored, triggering sync")
                triggerSyncOnConnectionRestore()
            }
            isConnected && !wasOffline -> {
                // Already online, no action needed
                Timber.d("Network connection available")
            }
        }
    }

    private fun triggerSyncOnConnectionRestore() {
        applicationScope.launch {
            try {
                // Trigger immediate sync
                dataSyncService.triggerImmediateSync()
                
                // Also trigger direct sync for immediate data availability
                val syncResults = dataSyncService.syncAllData()
                val successfulSyncs = syncResults.count { it.success }
                val totalSyncs = syncResults.size
                
                if (successfulSyncs > 0) {
                    Timber.d("Connection restore sync: $successfulSyncs/$totalSyncs syncs successful")
                } else {
                    Timber.w("Connection restore sync: No successful syncs")
                }
            } catch (e: Exception) {
                Timber.e(e, "Failed to perform connection restore sync")
            }
        }
    }

    private fun logCurrentSyncStatus() {
        applicationScope.launch {
            try {
                val syncInfo = syncStatusProvider.getDetailedSyncInfo()
                Timber.d("Current sync status: ${syncInfo.overallStatus}, last sync: ${syncInfo.formattedLastSyncTime}")
            } catch (e: Exception) {
                Timber.w(e, "Failed to get current sync status")
            }
        }
    }
}
 