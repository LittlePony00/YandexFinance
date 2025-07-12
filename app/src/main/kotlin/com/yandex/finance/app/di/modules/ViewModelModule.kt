package com.yandex.finance.app.di.modules

import androidx.lifecycle.ViewModel
import com.yandex.finance.core.dagger.ViewModelKey
import com.yandex.finance.feature.account.impl.presentation.viewmodel.MyAccountViewModel
import com.yandex.finance.feature.account.impl.presentation.viewmodel.MyAccountEditViewModel
import com.yandex.finance.feature.articles.presentation.viewmodel.MyArticlesViewModel
import com.yandex.finance.feature.settings.impl.presentation.viewmodel.SettingsViewModel
import com.yandex.finance.income.impl.presentation.viewmodel.IncomeTodayViewModel
import com.yandex.finance.income.impl.presentation.viewmodel.MyHistoryIncomeViewModel
import com.yandex.finance.outcome.impl.presentation.viewmodel.OutcomeTodayViewModel
import com.yandex.finance.outcome.impl.presentation.viewmodel.MyHistoryOutcomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Dagger module for binding ViewModels
 * Contains all ViewModels that use simple @Inject constructors
 */
@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MyAccountViewModel::class)
    fun bindMyAccountViewModel(viewModel: MyAccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MyAccountEditViewModel::class)
    fun bindMyAccountEditViewModel(viewModel: MyAccountEditViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MyArticlesViewModel::class)
    fun bindMyArticlesViewModel(viewModel: MyArticlesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IncomeTodayViewModel::class)
    fun bindIncomeTodayViewModel(viewModel: IncomeTodayViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MyHistoryIncomeViewModel::class)
    fun bindMyHistoryIncomeViewModel(viewModel: MyHistoryIncomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OutcomeTodayViewModel::class)
    fun bindOutcomeTodayViewModel(viewModel: OutcomeTodayViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MyHistoryOutcomeViewModel::class)
    fun bindMyHistoryOutcomeViewModel(viewModel: MyHistoryOutcomeViewModel): ViewModel
} 