package com.yandex.finance.outcome.impl.domain.usecase

import com.yandex.finance.core.data.repository.TransactionRepository
import com.yandex.finance.feature.outcome.api.domain.model.UiTransactionMainModel
import com.yandex.finance.feature.outcome.api.domain.model.asUiModel
import com.yandex.finance.feature.outcome.api.domain.usecase.GetUiCurrentCurrencyUseCase
import com.yandex.finance.feature.outcome.api.domain.usecase.GetUiTransactionByPeriodUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant
import timber.log.Timber
import javax.inject.Inject

class GetUiTransactionByPeriodUseCaseImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val getUiCurrentCurrencyUseCase: GetUiCurrentCurrencyUseCase,
    private val transactionRepository: TransactionRepository
) : GetUiTransactionByPeriodUseCase {

    override suspend operator fun invoke(
        id: String,
        startDate: String,
        endDate: String,
        isIncome: Boolean?
    ): Result<UiTransactionMainModel> = withContext(dispatcher) {
        Timber.d("GetUiTransactionByPeriodImpl was called. id: $id, startDate: $startDate, endDate: $endDate")

        transactionRepository.fetchTransactionsByPeriod(
            id = id,
            startDate = startDate,
            endDate = endDate
        ).mapCatching { transactions ->
            Timber.d("transactions: $transactions")
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
            }.sortedBy { transaction ->
                Instant.parse(transaction.updatedAt)
            }

            UiTransactionMainModel(
                isIncome = isIncome,
                sumOfAllTransactions = sum,
                transactions = incomeTransactions.asUiModel(),
                currentCurrencyType = getUiCurrentCurrencyUseCase(),
            )
        }
    }
}
