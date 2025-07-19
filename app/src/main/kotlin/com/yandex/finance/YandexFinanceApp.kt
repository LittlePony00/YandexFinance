package com.yandex.finance

import android.app.Application
import android.content.Context
import com.yandex.finance.app.di.DaggerMainActivityComponent
import com.yandex.finance.app.di.MainActivityComponent
import com.yandex.finance.core.common.HasDependencies
import com.yandex.finance.core.data.sync.OfflineManager
import timber.log.Timber
import javax.inject.Inject

class YandexFinanceApp : Application(), HasDependencies {

    lateinit var mainActivityComponent: MainActivityComponent
        private set

    @Inject
    lateinit var offlineManager: OfflineManager

    override val depsMap by lazy { mainActivityComponent.depsMap() }

    override fun onCreate() {
        super.onCreate()

        // Initialize Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // Initialize Dagger
        mainActivityComponent = DaggerMainActivityComponent.factory()
            .create(context = this)
        
        // Inject dependencies
        mainActivityComponent.inject(this)
        
        // Initialize offline functionality
        offlineManager.initialize()
    }
}

val Context.appComponent: MainActivityComponent
    get() = when(this) {
        is YandexFinanceApp -> mainActivityComponent
        else -> this.applicationContext.appComponent
    }
