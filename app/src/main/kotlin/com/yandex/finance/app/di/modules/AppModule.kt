package com.yandex.finance.app.di.modules

import android.content.Context
import com.yandex.finance.core.common.Id
import com.yandex.finance.core.network.impl.BuildConfig
import com.yandex.finance.core.ui.util.UiDateTimeFormatter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {
    
    @Provides
    @Singleton
    fun provideId(): Id = BuildConfig.ID
    
    @Provides
    @Singleton
    fun provideUiDateTimeFormatter(context: Context): UiDateTimeFormatter {
        return UiDateTimeFormatter(context)
    }
} 