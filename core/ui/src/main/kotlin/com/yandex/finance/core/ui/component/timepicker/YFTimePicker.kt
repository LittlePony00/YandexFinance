package com.yandex.finance.core.ui.component.timepicker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yandex.finance.core.ui.theme.YandexFinanceTheme
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YFTimePicker(
    onDismiss: () -> Unit,
    onConfirm: (TimePickerState) -> Unit,
    modifier: Modifier = Modifier,
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    TimePickerDialog(
        modifier = modifier,
        onDismiss = { onDismiss() },
        onConfirm = { onConfirm(timePickerState) },
        content = {
            TimePicker(state = timePickerState,)
        }
    )
}

@Composable
private fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Отменить")
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text("Сохранить")
            }
        },
        text = { content() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun YFTimePickerPreview() {
    YandexFinanceTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            YFTimePicker(
                onDismiss = {},
                onConfirm = {}
            )
        }
    }
}
