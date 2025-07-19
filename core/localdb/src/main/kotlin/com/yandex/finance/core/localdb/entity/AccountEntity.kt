package com.yandex.finance.core.localdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey
    val id: Int,
    val userId: Int? = null,
    val name: String,
    val balance: String,
    val currency: String,
    val createdAt: String,
    val updatedAt: String,
    
    // Sync metadata
    val isLocalOnly: Boolean = false,
    val isPendingSync: Boolean = false,
    val localCreatedAt: Long = System.currentTimeMillis(),
    val lastSyncAt: Long? = null,
    val syncVersion: Int = 1
) 