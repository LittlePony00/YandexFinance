package com.yandex.finance.core.ui.util

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.char

val dateTimeComponentsFormat = DateTimeComponents.Format {
    year()
    char('-')
    monthNumber()
    char('-')
    dayOfMonth()
}

val localDateFormatter = LocalDate.Format {
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
