package com.yandex.finance.feature.income.domain

import com.yandex.finance.core.ui.util.dateTimeComponentsFormat
import kotlinx.datetime.Clock
import kotlinx.datetime.format

data class UiMyHistoryModel(
    val uiIncomeMainModel: UiIncomeMainModel,
    val startDate: String,
    val endDate: String,
) {

    companion object {

        val initial
            get() = UiMyHistoryModel(
                uiIncomeMainModel = UiIncomeMainModel.initial,
                startDate = Clock.System.now().format(dateTimeComponentsFormat),
                endDate = Clock.System.now().format(dateTimeComponentsFormat)
            )
    }
}
