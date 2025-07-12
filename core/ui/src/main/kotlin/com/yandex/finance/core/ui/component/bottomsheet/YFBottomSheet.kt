package com.yandex.finance.core.ui.component.bottomsheet

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YFBottomSheet(
    onDismiss: () -> Unit,
    sheetState: SheetState,
    content: @Composable (ColumnScope.() -> Unit),
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        contentWindowInsets = { WindowInsets.systemBars },
    ) {
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
suspend fun SheetState.closeBottomSheet() { this.hide() }
