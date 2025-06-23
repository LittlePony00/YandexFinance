package com.yandex.finance.outcome.impl.domain.usecase

import com.yandex.finance.core.domain.repository.TransactionRepository
import com.yandex.finance.feature.outcome.api.domain.model.UiTransactionMainModel
import com.yandex.finance.feature.outcome.api.domain.model.asUiModel
import com.yandex.finance.feature.outcome.api.domain.usecase.GetUiTransactionByPeriod
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

class GetUiTransactionByPeriodImpl(
    private val dispatcher: CoroutineDispatcher,
    private val transactionRepository: TransactionRepository
) : GetUiTransactionByPeriod {

    override suspend operator fun invoke(
        id: String,
        startDate: String,
        endDate: String,
        isIncome: Boolean?
    ): Result<UiTransactionMainModel> = withContext(dispatcher) {
        Timber.d("GetUiTransactionByPeriodImpl was called")

        transactionRepository.fetchTransactionsByPeriod(
            id = id,
            startDate = startDate,
            endDate = endDate
        ).mapCatching { transactions ->
            var sum = 0.0
            val incomeTransactions = transactions.filter { transaction ->

                isIncome?.let {
                    if (transaction.category.isIncome == it) {
                        sum += transaction.amount.toDoubleOrNull() ?: 0.0
                        true
                    } else {
                        false
                    }
                } ?: run {
                    sum += transaction.amount.toDoubleOrNull() ?: 0.0
                    true
                }
            }

            UiTransactionMainModel(
                isIncome = isIncome,
                sumOfAllTransactions = sum,
                transactions = incomeTransactions.asUiModel()
            )
        }
    }
}
