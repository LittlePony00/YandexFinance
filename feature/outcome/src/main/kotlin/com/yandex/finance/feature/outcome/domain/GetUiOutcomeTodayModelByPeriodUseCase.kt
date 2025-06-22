package com.yandex.finance.feature.outcome.domain

import com.yandex.finance.core.domain.repository.TransactionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

class GetUiOutcomeTodayModelByPeriodUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val transactionRepository: TransactionRepository
) {

    suspend operator fun invoke(
        id: String,
        startDate: String,
        endDate: String,
        isIncome: Boolean? = null
    ): Result<UiOutcomeMainModel> = withContext(dispatcher) {
        Timber.d("GetUiIncomeTodayModelByPeriodUseCase was called")

        transactionRepository.fetchTransactionsByPeriod(id, startDate, endDate)
            .mapCatching { transactions ->
                var sum = 0.0
                val incomeTransactions = transactions.filter { transaction ->
                    sum += transaction.amount.toDoubleOrNull() ?: 0.0

                    isIncome?.let { transaction.category.isIncome == it } ?: true
                }

                UiOutcomeMainModel(
                    sumOfAllOutcomes = sum,
                    outcomes = incomeTransactions.asUiModel()
                )
            }
    }
}
