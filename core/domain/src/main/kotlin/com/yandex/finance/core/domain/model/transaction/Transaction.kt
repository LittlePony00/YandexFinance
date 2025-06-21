package com.yandex.finance.core.domain.model.transaction

import com.yandex.finance.core.domain.model.account.Account
import com.yandex.finance.core.domain.model.category.Category

data class Transaction(
    val id: Int,
    val amount: String,
    val comment: String?,
    val account: Account,
    val createdAt: String,
    val updatedAt: String,
    val category: Category,
    val transactionDate: String
)
