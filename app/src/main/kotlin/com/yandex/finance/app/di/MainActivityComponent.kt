package com.yandex.finance.app.di

import android.content.Context
import com.yandex.finance.app.di.modules.AccountDependenciesModule
import com.yandex.finance.app.di.modules.AppModule
import com.yandex.finance.app.di.modules.ArticlesDependenciesModule
import com.yandex.finance.app.di.modules.DataModule
import com.yandex.finance.app.di.modules.IncomeDependenciesModule
import com.yandex.finance.app.di.modules.NetworkModule
import com.yandex.finance.app.di.modules.OutcomeDependenciesModule
import com.yandex.finance.app.di.modules.OutcomeUseCaseModule
import com.yandex.finance.app.di.modules.SavedStateModule
import com.yandex.finance.app.di.modules.SettingsDependenciesModule
import com.yandex.finance.app.di.modules.ViewModelModule
import com.yandex.finance.app.presentation.activity.MainActivity
import com.yandex.finance.core.common.DepsMap
import com.yandex.finance.core.dagger.DaggerViewModelFactory
import com.yandex.finance.feature.account.api.di.AccountDependencies
import com.yandex.finance.feature.articles.api.di.ArticlesDependencies
import com.yandex.finance.feature.income.api.di.IncomeDependencies
import com.yandex.finance.feature.outcome.api.di.OutcomeDependencies
import com.yandex.finance.feature.settings.api.di.SettingsDependencies
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Main Dagger component that implements all feature Dependencies interfaces
 * This component provides all dependencies needed by feature modules
 */
@Component(
    modules = [
        AppModule::class,
        DataModule::class,
        NetworkModule::class,
        OutcomeUseCaseModule::class,
        ViewModelModule::class,
        SavedStateModule::class,
        AccountDependenciesModule::class,
        OutcomeDependenciesModule::class,
        IncomeDependenciesModule::class,
        ArticlesDependenciesModule::class,
        SettingsDependenciesModule::class
    ]
)
@Singleton
interface MainActivityComponent : 
    AccountDependencies,
    OutcomeDependencies,
    IncomeDependencies,
    ArticlesDependencies,
    SettingsDependencies {
    
    /**
     * Provides the map of all dependencies for use by HasDependencies
     */
    fun depsMap(): DepsMap
    
    /**
     * Provides the ViewModelFactory for creating simple ViewModels
     */
    fun viewModelFactory(): DaggerViewModelFactory
    
    /**
     * Inject dependencies into MainActivity
     */
    fun inject(activity: MainActivity)
    
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): MainActivityComponent
    }
} 