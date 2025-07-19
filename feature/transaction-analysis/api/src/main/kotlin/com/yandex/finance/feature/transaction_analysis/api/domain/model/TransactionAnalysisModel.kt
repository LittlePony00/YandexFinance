package com.yandex.finance.feature.transaction_analysis.api.domain.model

import androidx.compose.runtime.Immutable
import com.yandex.finance.core.domain.model.CurrencyType
import com.yandex.finance.core.ui.util.dateTimeComponentsFormat
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

@Immutable
data class TransactionAnalysisModel(
    val isIncome: Boolean,
    val startDate: String,
    val endDate: String,
    val totalAmount: Double,
    val currency: CurrencyType,
    val categoryAnalysis: List<CategoryAnalysisItem>
) {

    companion object {
        val initial: TransactionAnalysisModel
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

                return TransactionAnalysisModel(
                    isIncome = false,
                    startDate = firstDayOfMonth.format(dateTimeComponentsFormat),
                    endDate = now.format(dateTimeComponentsFormat),
                    totalAmount = 0.0,
                    currency = CurrencyType.RUB,
                    categoryAnalysis = emptyList()
                )
            }
    }
}

@Immutable
data class CategoryAnalysisItem(
    val categoryId: Int,
    val categoryName: String,
    val categoryEmoji: String?,
    val amount: Double,
    val percentage: Double,
    val description: String?
) 