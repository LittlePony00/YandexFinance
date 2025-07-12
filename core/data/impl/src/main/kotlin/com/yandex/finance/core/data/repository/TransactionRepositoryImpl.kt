package com.yandex.finance.core.data.repository

import com.yandex.finance.core.data.mapper.asExternalModel
import com.yandex.finance.core.data.mapper.asAnotherResult
import com.yandex.finance.core.data.mapper.asNetworkModel
import com.yandex.finance.core.domain.model.transaction.*
import com.yandex.finance.core.network.transaction.service.TransactionService
import timber.log.Timber

class TransactionRepositoryImpl(
    private val transactionService: TransactionService
) : TransactionRepository {

    override suspend fun createTransaction(body: TransactionWithoutId): Result<CreatedTransaction> {
        return transactionService.createTransaction(body.asNetworkModel()).asAnotherResult {
            it.asExternalModel()
        }
    }

    override suspend fun fetchTransactionById(id: String): Result<Transaction> {
        return transactionService.fetchTransactionById(id).asAnotherResult {
            it.asExternalModel()
        }
    }

    override suspend fun updateTransaction(
        id: Int,
        body: TransactionWithoutId
    ): Result<Transaction> {
        Timber.d("updateTransaction was called. id: $id, body: $body")
        return transactionService.updateTransaction(id, body.asNetworkModel()).asAnotherResult {
            it.asExternalModel()
        }.also {
            Timber.d("updateTransaction was called. ${it.getOrNull()}")
        }
    }

    override suspend fun deleteTransaction(id: Int): Result<Unit> {
        return transactionService.deleteTransaction(id)
    }

    override suspend fun fetchTransactionsByPeriod(
        id: String,
        startDate: String?,
        endDate: String?
    ): Result<List<Transaction>> {
        return transactionService.fetchTransactionsByPeriod(id, startDate, endDate)
            .asAnotherResult { transactionList ->
                transactionList.map { it.asExternalModel() }
            }
    }
}
