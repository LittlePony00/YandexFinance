package com.yandex.finance.feature.articles.domain

data class UiArticleModel(
    val id: Int,
    val icon: String,
    val title: String,
) {

    companion object {

        val initial
            get() = UiArticleModel(
                id = 0,
                icon = String(),
                title = String()
            )
    }
}
