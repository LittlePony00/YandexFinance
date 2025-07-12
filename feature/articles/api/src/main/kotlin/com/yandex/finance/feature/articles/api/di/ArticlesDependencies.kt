package com.yandex.finance.feature.articles.api.di

import com.yandex.finance.core.common.Dependencies
import com.yandex.finance.core.data.repository.CategoryRepository

/**
 * Dependencies interface for Articles feature
 * Defines all dependencies that Articles feature needs from external modules
 */
interface ArticlesDependencies : Dependencies {
    
    /**
     * Repository for category operations
     */
    val categoryRepository: CategoryRepository
}
