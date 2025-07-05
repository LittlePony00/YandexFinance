package com.yandex.finance.feature.account.impl.presentation.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yandex.finance.core.ui.R.string.american_dollar_name
import com.yandex.finance.core.ui.R.string.dollar_symbol
import com.yandex.finance.core.ui.R.string.euro_name
import com.yandex.finance.core.ui.R.string.euro_symbol
import com.yandex.finance.core.ui.R.string.russian_dollar_name
import com.yandex.finance.core.ui.R.string.russian_rubble_symbol
import com.yandex.finance.core.ui.component.bottomsheet.YFBottomSheet
import com.yandex.finance.core.ui.component.bottomsheet.closeBottomSheet
import com.yandex.finance.core.ui.component.icon.Cancel
import com.yandex.finance.core.ui.component.icon.DollarSymbol
import com.yandex.finance.core.ui.component.icon.EuroSymbol
import com.yandex.finance.core.ui.component.icon.RussianRubleSymbol
import com.yandex.finance.core.ui.component.listitem.ListItem
import com.yandex.finance.core.ui.theme.YandexFinanceTheme
import com.yandex.finance.feature.account.impl.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChooseCurrencyBottomSheet(
    onDismiss: () -> Unit,
    onEuroClick: () -> Unit,
    onDollarClick: () -> Unit,
    onRussianRubleClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val hideBottomSheet: () -> Unit = {
        scope.launch {
            sheetState.closeBottomSheet()
            onDismiss()
        }
    }

    YFBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismiss = hideBottomSheet,
        content = {
            ListItem(
                onClick = onRussianRubleClick,
                navigationContent = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.RussianRubleSymbol,
                        contentDescription = Icons.RussianRubleSymbol.name
                    )
                },
                content = {
                    Text(
                        text = "${
                            stringResource(russian_dollar_name)
                        } ${
                            stringResource(russian_rubble_symbol)
                        }"
                    )
                }
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            ListItem(
                onClick = onDollarClick,
                navigationContent = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.DollarSymbol,
                        contentDescription = Icons.DollarSymbol.name
                    )
                },
                content = {
                    Text(
                        text = "${
                            stringResource(american_dollar_name)
                        } ${
                            stringResource(dollar_symbol)
                        }"
                    )
                }
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            ListItem(
                onClick = onEuroClick,
                navigationContent = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.EuroSymbol,
                        contentDescription = Icons.EuroSymbol.name
                    )
                },
                content = {
                    Text(
                        text = "${
                            stringResource(euro_name)
                        } ${
                            stringResource(euro_symbol)
                        }"
                    )
                }
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            ListItem(
                onClick = hideBottomSheet,
                navigationContent = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Cancel,
                        tint = MaterialTheme.colorScheme.onError,
                        contentDescription = Icons.Cancel.name
                    )
                },
                containerColor = MaterialTheme.colorScheme.error,
                content = {
                    Text(
                        text = stringResource(R.string.cancel),
                        color = MaterialTheme.colorScheme.onError
                    )
                }
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        }
    )
}

@Preview
@Composable
private fun ChooseCurrencyBottomSheetPreview() {
    YandexFinanceTheme {
        ChooseCurrencyBottomSheet(
            modifier = Modifier,
            onDismiss = {},
            onEuroClick = {},
            onDollarClick = {},
            onRussianRubleClick = {},
        )
    }
}
