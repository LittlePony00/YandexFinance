package com.yandex.finance.core.ui.util

import android.content.Context
import com.yandex.finance.core.ui.R
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char

class UiDateTimeFormatter(private val context: Context) {

    val dateTimeComponentsFormat: (isNominative: Boolean) -> DateTimeFormat<DateTimeComponents> by lazy {
        {
            DateTimeComponents.Format {
                dayOfMonth()
                char(' ')
                monthName(names = if (it) monthNominativeStringIds else monthStrings)
                char(' ')
                year()
            }
        }
    }

    val localDateFormatter: (isNominative: Boolean) -> DateTimeFormat<LocalDate> by lazy {
        {
            LocalDate.Format {
                dayOfMonth()
                char(' ')
                monthName(names = if (it) monthNominativeStringIds else monthStrings)
                char(' ')
                year()
            }
        }
    }

    val localDateTimeFormatter: (isNominative: Boolean) -> DateTimeFormat<LocalDateTime> by lazy {
        {
            LocalDateTime.Format {
                dayOfMonth()
                char('.')
                monthName(names = if (it) monthNominativeStringIds else monthStrings)
                char('.')
                year()
                char(' ')
                hour()
                char(':')
                minute()
            }
        }
    }

    private val monthStrings by lazy {
        val monthIds = arrayOf(
            R.string.month_january,
            R.string.month_february,
            R.string.month_march,
            R.string.month_april,
            R.string.month_may,
            R.string.month_june,
            R.string.month_july,
            R.string.month_august,
            R.string.month_september,
            R.string.month_october,
            R.string.month_november,
            R.string.month_december
        )

        MonthNames(monthIds.map { context.getString(it) })
    }

    private val monthNominativeStringIds by lazy {
        val monthIds = arrayOf(
            R.string.month_january_nominative,
            R.string.month_february_nominative,
            R.string.month_march_nominative,
            R.string.month_april_nominative,
            R.string.month_may_nominative,
            R.string.month_june_nominative,
            R.string.month_july_nominative,
            R.string.month_august_nominative,
            R.string.month_september_nominative,
            R.string.month_october_nominative,
            R.string.month_november_nominative,
            R.string.month_december_nominative
        )

        MonthNames(monthIds.map { context.getString(it) })
    }
}

val dateTimeComponentsFormat = DateTimeComponents.Format {
    year()
    char('-')
    monthNumber()
    char('-')
    dayOfMonth()
//    dayOfMonth()
//    char(' ')
//    monthName(names = MonthNames.ENGLISH_FULL)
//    char(' ')
//    year()
}

val localDateTimeFormatter = LocalDateTime.Format {
    dayOfMonth()
    char('.')
    monthNumber()
    char('.')
    year()
    char(' ')
    hour()
    char(':')
    minute()
}




