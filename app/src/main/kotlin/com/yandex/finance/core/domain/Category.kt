package com.yandex.finance.core.domain

data class Category(
    val id: Int,
    val name: String,
    val emoji: String? = null,
    val isIncome: Boolean,
)
