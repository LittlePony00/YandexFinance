package com.yandex.finance.core.domain.model.category

data class Category(
    val id: Int,
    val name: String,
    val emoji: String? = null,
    val isIncome: Boolean,
)
