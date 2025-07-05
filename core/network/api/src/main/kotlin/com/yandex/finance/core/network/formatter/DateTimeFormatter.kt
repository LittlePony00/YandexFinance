package com.yandex.finance.core.network.formatter

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.char

object DateTimeFormatter {

    val dateTimeComponentsFormat = DateTimeComponents.Format {
        year()
        char('-')
        monthNumber()
        char('-')
        dayOfMonth()
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
}
