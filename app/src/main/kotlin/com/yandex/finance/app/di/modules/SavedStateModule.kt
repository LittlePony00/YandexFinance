package com.yandex.finance.app.di.modules

import androidx.lifecycle.SavedStateHandle
import dagger.Module
import dagger.Provides

@Module
object SavedStateModule {

    @Provides
    fun provideSavedStateHandle(): SavedStateHandle {
        return SavedStateHandle()
    }
} 