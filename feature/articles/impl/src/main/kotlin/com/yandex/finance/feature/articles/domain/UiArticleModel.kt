package com.yandex.finance.feature.articles.domain

import com.yandex.finance.core.domain.model.category.Category

data class UiArticleModel(
    val id: Int,
    val title: String,
    val emoji: String,
    val isIncome: Boolean,
) {

    companion object {

        val initial
            get() = UiArticleModel(
                id = 0,
                title = String(),
                emoji = String(),
                isIncome = false
            )
    }
}

fun Category.toUiArticleModel(): UiArticleModel {
    return UiArticleModel(
        id = id,
        title = name,
        emoji = emoji ?: "📄",
        isIncome = isIncome
    )
}
