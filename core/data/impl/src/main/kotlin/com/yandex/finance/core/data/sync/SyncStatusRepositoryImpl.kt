package com.yandex.finance.core.data.sync

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncStatusRepositoryImpl @Inject constructor(
    private val syncStatusProvider: SyncStatusProvider,
    private val offlineManager: OfflineManager
) : SyncStatusRepository {

    override suspend fun getFormattedLastSyncTime(): String {
        return syncStatusProvider.getFormattedOverallLastSyncTime()
    }

    override suspend fun getSyncInfo(): SyncInfo {
        val detailedInfo = syncStatusProvider.getDetailedSyncInfo()
        return SyncInfo(
            overallStatus = detailedInfo.overallStatus,
            lastSyncTime = detailedInfo.lastSyncTime,
            formattedLastSyncTime = detailedInfo.formattedLastSyncTime
        )
    }

    override suspend fun triggerManualSync(): Boolean {
        return offlineManager.triggerManualSync()
    }
}
