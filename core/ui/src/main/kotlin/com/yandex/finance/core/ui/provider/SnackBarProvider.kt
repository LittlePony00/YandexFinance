package com.yandex.finance.core.ui.provider

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import timber.log.Timber

val LocalSnackBarHostState =
    compositionLocalOf<SnackbarHostState> { error("SnackBarHostState is not initialized") }

@Stable
suspend fun SnackbarHostState.showMessage(
    message: String,
    withDismissAction: Boolean = true,
    duration: SnackbarDuration = SnackbarDuration.Indefinite
) {
    Timber.d("showMessage: $message")

    showSnackbar(
        message = message,
        duration = duration,
        withDismissAction = withDismissAction
    )
}

@Stable
suspend fun SnackbarHostState.showShortMessage(message: String) {
    Timber.d("showShortMessage: $message")

    showSnackbar(
        message = message,
        withDismissAction = true,
        duration = SnackbarDuration.Short
    )
}

@Stable
suspend fun SnackbarHostState.showLongMessage(message: String) {
    Timber.d("showLongMessage: $message")

    showSnackbar(
        message = message,
        withDismissAction = true,
        duration = SnackbarDuration.Long
    )
}
