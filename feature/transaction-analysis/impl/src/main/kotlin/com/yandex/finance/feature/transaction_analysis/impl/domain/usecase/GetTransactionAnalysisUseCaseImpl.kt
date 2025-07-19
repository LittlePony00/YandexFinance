package com.yandex.finance.feature.transaction_analysis.impl.domain.usecase

import com.yandex.finance.core.data.repository.TransactionRepository
import com.yandex.finance.feature.transaction_analysis.api.domain.model.CategoryAnalysisItem
import com.yandex.finance.feature.transaction_analysis.api.domain.model.TransactionAnalysisModel
import com.yandex.finance.feature.transaction_analysis.api.domain.usecase.GetTransactionAnalysisUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class GetTransactionAnalysisUseCaseImpl @Inject constructor(
    @Named("IO") private val dispatcher: CoroutineDispatcher,
    private val transactionRepository: TransactionRepository
) : GetTransactionAnalysisUseCase {

    override suspend operator fun invoke(
        id: String,
        startDate: String,
        endDate: String,
        isIncome: Boolean
    ): Result<TransactionAnalysisModel> = withContext(dispatcher) {
        Timber.d("GetTransactionAnalysisUseCaseImpl was called. id: $id, startDate: $startDate, endDate: $endDate, isIncome: $isIncome")

        transactionRepository.fetchTransactionsByPeriod(
            id = id,
            startDate = startDate,
            endDate = endDate
        ).mapCatching { transactions ->
            Timber.d("transactions: $transactions")
            
            val filteredTransactions = transactions.filter { transaction ->
                transaction.category.isIncome == isIncome
            }

            val groupedByCategory = filteredTransactions.groupBy { transaction ->
                transaction.category
            }

            val totalAmount = filteredTransactions.sumOf { transaction ->
                transaction.amount.toDoubleOrNull() ?: 0.0
            }

            val categoryAnalysis = groupedByCategory.map { (category, categoryTransactions) ->
                val categoryAmount = categoryTransactions.sumOf { transaction ->
                    transaction.amount.toDoubleOrNull() ?: 0.0
                }
                val percentage = if (totalAmount > 0) (categoryAmount / totalAmount) * 100 else 0.0
                
                CategoryAnalysisItem(
                    categoryId = category.id,
                    categoryName = category.name,
                    categoryEmoji = category.emoji,
                    amount = categoryAmount,
                    percentage = percentage,
                    description = categoryTransactions.firstOrNull()?.comment
                )
            }.sortedByDescending { it.amount }

            TransactionAnalysisModel(
                isIncome = isIncome,
                startDate = startDate,
                endDate = endDate,
                totalAmount = totalAmount,
                currency = com.yandex.finance.core.domain.model.CurrencyType.RUB,
                categoryAnalysis = categoryAnalysis
            )
        }
    }
} 