package com.yandex.finance.core.data.sync

import kotlin.random.Random

/**
 * Utility for generating local IDs for offline items
 * Uses negative numbers to avoid conflicts with server IDs
 */
object LocalIdGenerator {
    
    private val random = Random.Default
    
    /**
     * Generate a negative ID for local-only items
     * Range: -1000000 to -1 to avoid conflicts with server IDs
     */
    fun generateLocalId(): Int {
        return -random.nextInt(1, 1000000)
    }
    
    /**
     * Check if an ID is a local ID (negative)
     */
    fun isLocalId(id: Int): Boolean {
        return id < 0
    }
    
    /**
     * Check if an ID is a server ID (positive)
     */
    fun isServerId(id: Int): Boolean {
        return id > 0
    }
} 