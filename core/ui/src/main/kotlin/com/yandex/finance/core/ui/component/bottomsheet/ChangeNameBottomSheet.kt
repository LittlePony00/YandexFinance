package com.yandex.finance.core.ui.component.bottomsheet

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yandex.finance.core.ui.R
import com.yandex.finance.core.ui.component.button.PrimaryButton
import com.yandex.finance.core.ui.theme.RobotoTitleLargeStyle
import com.yandex.finance.core.ui.theme.YandexFinanceTheme
import com.yandex.finance.core.ui.util.SpacerHeight
import com.yandex.finance.core.ui.validator.AccountValidator
import com.yandex.finance.core.ui.validator.validationErrorToString
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeNameBottomSheet(
    initialValue: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit,
    modifier: Modifier = Modifier,
    validationError: String? = null
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var localValue by remember { mutableStateOf(initialValue) }
    val validator = remember { AccountValidator() }
    
    val localValidationError = validator.validateAccountName(localValue)
    val displayedError = localValidationError?.let { stringResource(it.validationErrorToString()) } ?: validationError

    val hideBottomSheet: () -> Unit = {
        scope.launch {
            onDismiss()
            sheetState.closeBottomSheet()
        }
    }

    YFBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismiss = hideBottomSheet,
        content = {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.change_account_name),
                    style = RobotoTitleLargeStyle.copy(fontWeight = FontWeight.Bold)
                )
                IconButton(onClick = hideBottomSheet) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = Icons.Default.Close.name
                    )
                }
            }
            SpacerHeight(height = 16.dp)
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                label = {
                    Text(
                        text = stringResource(R.string.account_name),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.enter_account_name),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                },
                value = localValue,
                onValueChange = { localValue = it },
                isError = displayedError != null,
                supportingText = displayedError?.let { error ->
                    {
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            )
            SpacerHeight(height = 16.dp)
            PrimaryButton(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.save_changes),
                onButtonClick = { onSave(localValue) }
            )
            SpacerHeight(height = 16.dp)
        }
    )
}

@Preview
@Composable
private fun ChangeAccountNameBottomSheetPreview() {
    YandexFinanceTheme {
        ChangeNameBottomSheet(
            initialValue = "",
            onDismiss = {},
            onSave = {}
        )
    }
}
