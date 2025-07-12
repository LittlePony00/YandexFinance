package com.yandex.finance.feature.transaction_edit.impl.presentation.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yandex.finance.core.ui.component.listitem.ListItem

@Composable
internal fun ListItemForTransactionEdit(
    onClick: () -> Unit,
    trailingText: String,
    navigationText: String,
    modifier: Modifier = Modifier,
    isTrailingIconEnabled: Boolean = false,
) {
    ListItem(
        modifier = modifier,
        trailingContent = {
            Text(text = trailingText)
            if (isTrailingIconEnabled) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    tint = MaterialTheme.colorScheme.outlineVariant,
                    contentDescription = Icons.AutoMirrored.Filled.KeyboardArrowRight.name
                )
            }
        },
        navigationContent = {
            Text(text = navigationText)
        },
        onClick = onClick
    )
}

@Preview
@Composable
private fun ListItemForTransactionEditPreview() {
    ListItemForTransactionEdit(
        trailingText = "Сбербанк",
        navigationText = "Счёт",
        onClick = {},
        isTrailingIconEnabled = true
    )
}
