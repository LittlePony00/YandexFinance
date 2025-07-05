package com.yandex.finance.feature.outcome.api.domain.usecase

import com.yandex.finance.core.domain.UseCase
import com.yandex.finance.feature.outcome.api.domain.model.UiTransactionMainModel

/**
 * Use case for getting UI transaction data by period
 */
interface GetUiTransactionByPeriodUseCase : UseCase {

    /**
     * Gets UI transaction data for specified period
     *
     * @param id account id
     * @param startDate start date of period
     * @param endDate end date of period
     * @param isIncome filter by transaction type (optional): true for income, false for expense, null for all
     *
     * @return Result<[UiTransactionMainModel]>
     */
    suspend operator fun invoke(
        id: String,
        startDate: String,
        endDate: String,
        isIncome: Boolean? = null
    ): Result<UiTransactionMainModel>
}
