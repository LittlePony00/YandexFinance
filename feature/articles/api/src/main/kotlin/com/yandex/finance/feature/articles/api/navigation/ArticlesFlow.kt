package com.yandex.finance.feature.articles.api.navigation

import com.yandex.finance.core.ui.navigation.Graph
import kotlinx.serialization.Serializable

@Serializable
object ArticlesGraph: Graph

sealed interface ArticlesFlow {

    @Serializable
    data object MyArticles : ArticlesFlow
}
