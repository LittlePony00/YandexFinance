package com.yandex.finance

import android.app.Application
import com.yandex.finance.app.di.provideAppModule
import com.yandex.finance.core.common.provideCoroutineScopesModule
import com.yandex.finance.core.common.provideDispatcherModule
import com.yandex.finance.core.data.di.provideDataModule
import com.yandex.finance.core.network.di.provideNetworkModule
import com.yandex.finance.core.network.di.provideNetworkServiceModule
import com.yandex.finance.feature.account.impl.di.provideAccountModule
import com.yandex.finance.income.impl.di.provideIncomeModule
import com.yandex.finance.outcome.impl.di.provideOutcomeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class YandexFinanceApp: Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // Initialize Koin
        startKoin {
            androidLogger()
            androidContext(this@YandexFinanceApp)

            // app module
            modules(provideAppModule)

            // common module
            modules(provideDispatcherModule, provideCoroutineScopesModule)

            // data module
            modules(provideDataModule)

            // network module
            modules(provideNetworkModule, provideNetworkServiceModule)

            // account module
            modules(provideAccountModule)

            // outcome module
            modules(provideOutcomeModule)

            // income module
            modules(provideIncomeModule)
        }
    }
}
