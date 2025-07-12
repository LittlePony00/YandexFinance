package com.yandex.finance.app.di.modules

import com.yandex.finance.app.di.MainActivityComponent
import com.yandex.finance.core.common.Dependencies
import com.yandex.finance.core.dagger.DependenciesKey
import com.yandex.finance.feature.articles.api.di.ArticlesDependencies
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ArticlesDependenciesModule {
    
    @Binds
    @IntoMap
    @DependenciesKey(ArticlesDependencies::class)
    fun bindArticlesDependencies(impl: MainActivityComponent): Dependencies
} 