package com.yandex.finance.feature.income.domain

import com.yandex.finance.core.domain.repository.TransactionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

class GetUiIncomeTodayModelByPeriodUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val transactionRepository: TransactionRepository
) {

    suspend operator fun invoke(
        id: String,
        startDate: String,
        endDate: String,
    ): Result<UiIncomeMainModel> = withContext(dispatcher) {
        Timber.d("GetUiIncomeTodayModelByPeriodUseCase was called")

        transactionRepository.fetchTransactionsByPeriod(id, startDate, endDate)
            .mapCatching { transactions ->
                var sum = 0.0
                val incomeTransactions = transactions.filter {
                    sum += it.amount.toDoubleOrNull() ?: 0.0

                    it.category.isIncome
                }

                UiIncomeMainModel(
                    sumOfAllIncomes = sum,
                    incomes = incomeTransactions.asUiModel()
                )
            }
    }
}
