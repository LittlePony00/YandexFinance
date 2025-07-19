package com.yandex.finance.core.localdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val emoji: String? = null,
    val isIncome: Boolean,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    
    // Sync metadata
    val lastSyncAt: Long? = null
) 