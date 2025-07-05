package com.yandex.finance.income.impl.domain.model

import com.yandex.finance.core.ui.util.dateTimeComponentsFormat
import com.yandex.finance.feature.outcome.api.domain.model.UiMyHistoryModel
import com.yandex.finance.feature.outcome.api.domain.model.UiTransactionMainModel
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

internal val UiMyHistoryModel.Companion.initialIncome: UiMyHistoryModel
    get() {
        val now = Clock.System.now()
        val localDateTime = now.toLocalDateTime(TimeZone.UTC)

        val firstDayOfMonth = LocalDateTime(
            year = localDateTime.year,
            month = localDateTime.month,
            dayOfMonth = 1,
            hour = 0,
            minute = 0,
            second = 0
        ).toInstant(TimeZone.UTC)

        return UiMyHistoryModel(
            uiTransactionMainModel = UiTransactionMainModel.Companion.initialIncome,
            startDate = firstDayOfMonth.format(dateTimeComponentsFormat),
            endDate = now.format(dateTimeComponentsFormat)
        )
    }
