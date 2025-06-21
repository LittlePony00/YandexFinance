package com.yandex.finance.core.navigation.articles

import kotlinx.serialization.Serializable

@Serializable
object ArticlesGraph: com.yandex.finance.core.navigation.Graph

sealed interface ArticlesFlow {

    @Serializable
    data object MyArticles : ArticlesFlow
}
