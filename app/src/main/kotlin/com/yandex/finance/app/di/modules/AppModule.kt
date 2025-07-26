package com.yandex.finance.app.di.modules

import android.app.Application
import android.content.Context
import com.yandex.finance.BuildConfig
import com.yandex.finance.core.common.Id
import com.yandex.finance.core.network.impl.BuildConfig as NetworkBuildConfig
import com.yandex.finance.core.ui.util.UiDateTimeFormatter
import com.yandex.finance.core.common.AppInfoProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {
    
    @Provides
    @Singleton
    fun provideApplication(context: Context): Application {
        return context.applicationContext as Application
    }
    
    @Provides
    @Singleton
    fun provideId(): Id = NetworkBuildConfig.ID

    @Provides
    @Singleton
    fun provideAppInfoProvider(): AppInfoProvider = object : AppInfoProvider {
        override val appVersion: String = BuildConfig.VERSION_NAME
    }
    
    @Provides
    @Singleton
    fun provideUiDateTimeFormatter(context: Context): UiDateTimeFormatter {
        return UiDateTimeFormatter(context)
    }
} 