package com.yandex.finance.core.data.repository

import com.yandex.finance.core.data.mapper.asExternalModel
import com.yandex.finance.core.data.mapper.asExternalResult
import com.yandex.finance.core.data.mapper.asNetworkModel
import com.yandex.finance.core.domain.model.transaction.*
import com.yandex.finance.core.domain.repository.TransactionRepository
import com.yandex.finance.core.network.transaction.service.TransactionService

class TransactionRepositoryImpl(
    private val transactionService: TransactionService
) : TransactionRepository {

    override suspend fun createTransaction(body: TransactionWithoutId): Result<CreatedTransaction> {
        return transactionService.createTransaction(body.asNetworkModel()).asExternalResult {
            it.asExternalModel()
        }
    }

    override suspend fun fetchTransactionById(id: String): Result<Transaction> {
        return transactionService.fetchTransactionById(id).asExternalResult {
            it.asExternalModel()
        }
    }

    override suspend fun updateTransaction(
        id: String,
        body: TransactionWithoutId
    ): Result<Transaction> {
        return transactionService.updateTransaction(id, body.asNetworkModel()).asExternalResult {
            it.asExternalModel()
        }
    }

    override suspend fun deleteTransaction(id: String): Result<Boolean> {
        return transactionService.deleteTransaction(id)
    }

    override suspend fun fetchTransactionsByPeriod(
        id: String,
        startDate: String?,
        endDate: String?
    ): Result<List<Transaction>> {
        return transactionService.fetchTransactionsByPeriod(id, startDate, endDate)
            .asExternalResult { transactionList ->
                transactionList.map { it.asExternalModel() }
            }
    }
}
