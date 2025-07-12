package com.yandex.finance.core.data.repository

import com.yandex.finance.core.domain.model.transaction.*

/**
 * Interface for work with transactions
 */
interface TransactionRepository {

    /**
     * Creates a new transaction
     *
     * @param body [TransactionWithoutId]
     *
     * @return Result<[CreatedTransaction]>
     */
    suspend fun createTransaction(body: TransactionWithoutId): Result<CreatedTransaction>

    /**
     * Fetches a single transaction by ID
     *
     * @param id transaction id
     *
     * @return Result<[Transaction]>
     */
    suspend fun fetchTransactionById(id: Int): Result<Transaction>

    /**
     * Updates an existing transaction
     *
     * @param id transaction id
     * @param body [TransactionWithoutId]
     *
     * @return Result<[Transaction]>
     */
    suspend fun updateTransaction(
        id: Int,
        body: TransactionWithoutId
    ): Result<Transaction>

    /**
     * Deletes a transaction by ID
     *
     * @param id transaction id
     *
     * @return Result<[Boolean]> if transaction was removed then returns true, else false
     */
    suspend fun deleteTransaction(id: Int): Result<Unit>

    /**
     * Fetches transactions by period
     *
     * @param id account id
     * @param startDate start date filter (optional)
     * @param endDate end date filter (optional)
     *
     * @return Result<List<[Transaction]>>
     */
    suspend fun fetchTransactionsByPeriod(
        id: String,
        startDate: String? = null,
        endDate: String? = null
    ): Result<List<Transaction>>
}
