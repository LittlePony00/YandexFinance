package com.yandex.finance.feature.transaction_analysis.api.domain.usecase

import com.yandex.finance.core.domain.UseCase
import com.yandex.finance.feature.transaction_analysis.api.domain.model.TransactionAnalysisModel

/**
 * Use case for getting transaction analysis data
 */
interface GetTransactionAnalysisUseCase : UseCase {

    /**
     * Gets transaction analysis data for specified period
     *
     * @param id account id
     * @param startDate start date of period
     * @param endDate end date of period
     * @param isIncome filter by transaction type: true for income, false for expense
     *
     * @return Result<[TransactionAnalysisModel]>
     */
    suspend operator fun invoke(
        id: String,
        startDate: String,
        endDate: String,
        isIncome: Boolean
    ): Result<TransactionAnalysisModel>
} 