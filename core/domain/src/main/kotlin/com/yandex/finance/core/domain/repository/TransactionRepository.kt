package com.yandex.finance.core.domain.repository

import com.yandex.finance.core.domain.model.transaction.*

interface TransactionRepository {

    suspend fun createTransaction(body: TransactionWithoutId): Result<CreatedTransaction>

    suspend fun fetchTransactionById(id: String): Result<Transaction>

    suspend fun updateTransaction(
        id: String,
        body: TransactionWithoutId
    ): Result<Transaction>

    suspend fun deleteTransaction(id: String): Result<Boolean>

    suspend fun fetchTransactionsByPeriod(
        id: String,
        startDate: String? = null,
        endDate: String? = null
    ): Result<List<Transaction>>
}
