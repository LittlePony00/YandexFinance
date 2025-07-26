package com.yandex.finance.core.datastore

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object PinCodeStorage {
    private const val PREFS_NAME = "pin_code_prefs"
    private const val PIN_KEY = "pin_code"

    private fun getPrefs(context: Context) = EncryptedSharedPreferences.create(
        context,
        PREFS_NAME,
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    suspend fun setPinCode(context: Context, pin: String) = withContext(Dispatchers.IO) {
        getPrefs(context).edit().putString(PIN_KEY, pin).apply()
    }

    suspend fun getPinCode(context: Context): String? = withContext(Dispatchers.IO) {
        getPrefs(context).getString(PIN_KEY, null)
    }

    suspend fun clearPinCode(context: Context) = withContext(Dispatchers.IO) {
        getPrefs(context).edit().remove(PIN_KEY).apply()
    }
} 