package com.yandex.finance

import android.app.Application
import timber.log.Timber

class YandexFinanceApp: Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
