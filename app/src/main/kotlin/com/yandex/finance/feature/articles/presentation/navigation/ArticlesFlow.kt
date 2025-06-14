package com.yandex.finance.feature.articles.presentation.navigation

import com.yandex.finance.core.ui.Graph
import kotlinx.serialization.Serializable

@Serializable
object ArticlesGraph: Graph

sealed interface ArticlesFlow {

    @Serializable
    data object MyArticles : ArticlesFlow
}
