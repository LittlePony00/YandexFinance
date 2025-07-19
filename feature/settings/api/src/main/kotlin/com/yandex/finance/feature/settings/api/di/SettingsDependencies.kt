package com.yandex.finance.feature.settings.api.di

import com.yandex.finance.core.common.Dependencies
import com.yandex.finance.core.data.sync.SyncStatusRepository

/**
 * Dependencies interface for Settings feature
 * Defines all dependencies that Settings feature needs from external modules
 */
interface SettingsDependencies : Dependencies {
    val syncStatusRepository: SyncStatusRepository
} 