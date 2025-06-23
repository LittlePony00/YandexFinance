package com.yandex.finance.feature.outcome.api.domain.model

import com.yandex.finance.core.ui.util.dateTimeComponentsFormat
import kotlinx.datetime.Clock
import kotlinx.datetime.format

data class UiMyHistoryModel(
    val uiTransactionMainModel: UiTransactionMainModel,
    val startDate: String,
    val endDate: String,
) {

    companion object {

        fun initialIncome() = UiMyHistoryModel(
            uiTransactionMainModel = UiTransactionMainModel.initialIncome(),
            startDate = Clock.System.now().format(dateTimeComponentsFormat),
            endDate = Clock.System.now().format(dateTimeComponentsFormat)
        )

        fun initialOutcome() = UiMyHistoryModel(
            uiTransactionMainModel = UiTransactionMainModel.initialOutcome(),
            startDate = Clock.System.now().format(dateTimeComponentsFormat),
            endDate = Clock.System.now().format(dateTimeComponentsFormat)
        )
    }
}
