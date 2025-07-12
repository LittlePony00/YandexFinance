package com.yandex.finance.feature.articles.di

import com.yandex.finance.feature.articles.api.di.ArticlesDependencies
import dagger.Component

/**
 * Internal Dagger component for Articles feature
 * Uses dependencies from ArticlesDependencies interface
 */
@Component(dependencies = [ArticlesDependencies::class])
internal interface ArticlesComponent {
    
    /**
     * Factory for creating ArticlesComponent with dependencies
     */
    @Component.Factory
    interface Factory {
        fun create(deps: ArticlesDependencies): ArticlesComponent
    }
} 