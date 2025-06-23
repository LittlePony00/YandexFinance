package com.yandex.finance.core.ui.component.calendar

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yandex.finance.core.ui.theme.RobotoLabelLargeStyle
import com.yandex.finance.core.ui.theme.YandexFinanceTheme
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YandexFinanceCalendar(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    datePickerState: DatePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis <= Clock.System.now().toEpochMilliseconds()
            }

            override fun isSelectableYear(year: Int): Boolean {
                return year <= Clock.System.now()
                    .toLocalDateTime(TimeZone.currentSystemDefault()).year
            }
        },
    )
) {
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text(
                    text = "OK",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = RobotoLabelLargeStyle
                )
            }
        },
        colors = DatePickerDefaults.colors(containerColor = MaterialTheme.colorScheme.secondary),
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "Cancel",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = RobotoLabelLargeStyle
                )
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            modifier = modifier,
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.secondary,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
                headlineContentColor = MaterialTheme.colorScheme.onSurface,
                weekdayContentColor = MaterialTheme.colorScheme.onSurface,
                subheadContentColor = MaterialTheme.colorScheme.onSurface,
                yearContentColor = MaterialTheme.colorScheme.onSurface,
                currentYearContentColor = MaterialTheme.colorScheme.onSurface,
                dayContentColor = MaterialTheme.colorScheme.onSurface,
                disabledDayContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                selectedDayContentColor = MaterialTheme.colorScheme.onPrimary,
                disabledSelectedDayContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.38f),
                selectedDayContainerColor = MaterialTheme.colorScheme.primary,
                disabledSelectedDayContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.38f),
                todayContentColor = MaterialTheme.colorScheme.primary,
                todayDateBorderColor = MaterialTheme.colorScheme.primary,
                dayInSelectionRangeContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                dayInSelectionRangeContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                navigationContentColor = MaterialTheme.colorScheme.onSurface,
                dividerColor = MaterialTheme.colorScheme.outline
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun YandexFinanceCalendarPreview() {
    YandexFinanceTheme {
        YandexFinanceCalendar(
            onDateSelected = {},
            onDismiss = {},
        )
    }
}
