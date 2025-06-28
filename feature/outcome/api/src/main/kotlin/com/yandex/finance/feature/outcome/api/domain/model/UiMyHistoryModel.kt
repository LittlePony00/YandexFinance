package com.yandex.finance.feature.outcome.api.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class UiMyHistoryModel(
    val endDate: String,
    val startDate: String,
    val uiTransactionMainModel: UiTransactionMainModel,
) {

    companion object
}
