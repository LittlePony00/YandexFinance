package com.yandex.finance.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object SettingsDataStore {
    private const val DATASTORE_NAME = "settings_datastore"

    private val Context.dataStore by preferencesDataStore(DATASTORE_NAME)

    // Keys
    val THEME_DARK_KEY = booleanPreferencesKey("theme_dark")
    val MAIN_COLOR_KEY = stringPreferencesKey("main_color")
    val HAPTICS_ENABLED_KEY = booleanPreferencesKey("haptics_enabled")
    val HAPTICS_EFFECT_KEY = stringPreferencesKey("haptics_effect")
    val PIN_CODE_KEY = stringPreferencesKey("pin_code")
    val SYNC_FREQUENCY_KEY = intPreferencesKey("sync_frequency")
    val LOCALE_KEY = stringPreferencesKey("locale")

    // Theme
    fun isDarkTheme(context: Context): Flow<Boolean> =
        context.dataStore.data.map { it[THEME_DARK_KEY] ?: false }
    suspend fun setDarkTheme(context: Context, isDark: Boolean) {
        context.dataStore.edit { it[THEME_DARK_KEY] = isDark }
    }

    // Main Color
    fun mainColor(context: Context): Flow<String?> =
        context.dataStore.data.map { it[MAIN_COLOR_KEY] }
    suspend fun setMainColor(context: Context, color: String) {
        context.dataStore.edit { it[MAIN_COLOR_KEY] = color }
    }

    // Haptics
    fun isHapticsEnabled(context: Context): Flow<Boolean> =
        context.dataStore.data.map { it[HAPTICS_ENABLED_KEY] ?: false }
    suspend fun setHapticsEnabled(context: Context, enabled: Boolean) {
        context.dataStore.edit { it[HAPTICS_ENABLED_KEY] = enabled }
    }
    fun hapticsEffect(context: Context): Flow<String?> =
        context.dataStore.data.map { it[HAPTICS_EFFECT_KEY] }
    suspend fun setHapticsEffect(context: Context, effect: String) {
        context.dataStore.edit { it[HAPTICS_EFFECT_KEY] = effect }
    }

    // Pin Code
    fun pinCode(context: Context): Flow<String?> =
        context.dataStore.data.map { it[PIN_CODE_KEY] }
    suspend fun setPinCode(context: Context, pin: String) {
        context.dataStore.edit { it[PIN_CODE_KEY] = pin }
    }

    // Sync Frequency
    fun syncFrequency(context: Context): Flow<Int> =
        context.dataStore.data.map { it[SYNC_FREQUENCY_KEY] ?: 24 }
    suspend fun setSyncFrequency(context: Context, hours: Int) {
        context.dataStore.edit { it[SYNC_FREQUENCY_KEY] = hours }
    }

    // Locale
    fun locale(context: Context): Flow<String> =
        context.dataStore.data.map { it[LOCALE_KEY] ?: "ru" }
    suspend fun setLocale(context: Context, locale: String) {
        context.dataStore.edit { it[LOCALE_KEY] = locale }
    }
} 