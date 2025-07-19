package com.yandex.finance.core.localdb.dao

import androidx.room.*
import com.yandex.finance.core.localdb.entity.SyncMetadataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SyncMetadataDao {

    @Query("SELECT * FROM sync_metadata ORDER BY lastSuccessfulSync DESC")
    fun getAllSyncMetadata(): Flow<List<SyncMetadataEntity>>

    @Query("SELECT * FROM sync_metadata WHERE dataType = :dataType")
    suspend fun getSyncMetadata(dataType: String): SyncMetadataEntity?

    @Query("SELECT lastSuccessfulSync FROM sync_metadata WHERE dataType = :dataType")
    suspend fun getLastSyncTime(dataType: String): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSyncMetadata(syncMetadata: SyncMetadataEntity)

    @Update
    suspend fun updateSyncMetadata(syncMetadata: SyncMetadataEntity)

    @Query("UPDATE sync_metadata SET lastSuccessfulSync = :timestamp, lastSyncAttempt = :timestamp, syncStatus = 'SUCCESS', errorMessage = null WHERE dataType = :dataType")
    suspend fun markSyncSuccess(dataType: String, timestamp: Long)

    @Query("UPDATE sync_metadata SET lastSyncAttempt = :timestamp, syncStatus = 'FAILED', errorMessage = :errorMessage WHERE dataType = :dataType")
    suspend fun markSyncFailed(dataType: String, timestamp: Long, errorMessage: String)

    @Query("UPDATE sync_metadata SET syncStatus = 'IN_PROGRESS', lastSyncAttempt = :timestamp WHERE dataType = :dataType")
    suspend fun markSyncInProgress(dataType: String, timestamp: Long)

    @Query("UPDATE sync_metadata SET pendingChanges = :count WHERE dataType = :dataType")
    suspend fun updatePendingChanges(dataType: String, count: Int)

    @Query("DELETE FROM sync_metadata")
    suspend fun clearAll()
} 