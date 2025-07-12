package com.yandex.finance.core.network.transaction.service

import com.yandex.finance.core.network.transaction.model.NetworkCreatedTransaction
import com.yandex.finance.core.network.transaction.model.NetworkTransaction
import com.yandex.finance.core.network.transaction.model.NetworkTransactionWithoutId

interface TransactionService {

    suspend fun createTransaction(body: NetworkTransactionWithoutId): Result<NetworkCreatedTransaction>

    suspend fun fetchTransactionById(id: String): Result<NetworkTransaction>

    suspend fun updateTransaction(
        id: Int,
        body: NetworkTransactionWithoutId
    ): Result<NetworkTransaction>

    suspend fun deleteTransaction(id: Int): Result<Unit>

    suspend fun fetchTransactionsByPeriod(
        id: String,
        startDate: String? = null,
        endDate: String? = null
    ): Result<List<NetworkTransaction>>
}
