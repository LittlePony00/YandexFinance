package com.yandex.finance.feature.transaction_edit.impl.presentation.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yandex.finance.core.domain.model.category.Category
import com.yandex.finance.core.ui.component.bottomsheet.YFBottomSheet
import com.yandex.finance.core.ui.component.icon.EmojiWrapper
import com.yandex.finance.core.ui.component.icon.Search
import com.yandex.finance.core.ui.component.listitem.ListItem
import com.yandex.finance.core.ui.theme.RobotoBodyLargeStyle
import com.yandex.finance.core.ui.theme.YandexFinanceTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)
@Composable
fun CategoryBottomSheet(
    list: List<Category>,
    onDismiss: () -> Unit,
    onClick: (Category) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val hideBottomSheet: () -> Unit = {
        scope.launch {
            sheetState.hide()
            onDismiss()
        }
    }

    var filterText by rememberSaveable { mutableStateOf("") }
    var mutableList by rememberSaveable { mutableStateOf(list) }

    LaunchedEffect(list) {
        launch(Dispatchers.Default) {
            snapshotFlow { filterText }
                .debounce(500)
                .distinctUntilChanged()
                .collectLatest { name ->
                    mutableList = list.filter {
                        it.name.contains(name)
                    }
                }
        }
    }

    YFBottomSheet(
        sheetState = sheetState,
        onDismiss = hideBottomSheet,
        content = {
            LazyColumn(
                modifier = modifier
            ) {
                item {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        value = filterText,
                        placeholder = {
                            Text(
                                text = "Найти категорию",
                                style = RobotoBodyLargeStyle,
                                color = MaterialTheme.colorScheme.surfaceVariant
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                            focusedIndicatorColor = MaterialTheme.colorScheme.outlineVariant,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.outlineVariant,
                        ),
                        onValueChange = {
                            filterText = it
                        },
                        trailingIcon = {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = Icons.Search,
                                tint = MaterialTheme.colorScheme.surfaceVariant,
                                contentDescription = Icons.Search.name
                            )
                        }
                    )
                }
                items(mutableList) { category ->
                    ListItem(
                        onClick = {
                            hideBottomSheet()
                            onClick(category)
                        },
                        content = {
                            Text(text = category.name)
                        },
                        navigationContent = {
                            EmojiWrapper(
                                text = category.name,
                                emoji = category.emoji
                            )
                        }
                    )
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                }
            }
        }
    )
}

@Preview
@Composable
private fun CategoryBottomSheetPreview() {
    YandexFinanceTheme {
        CategoryBottomSheet(
            list = listOf(
                Category(
                    id = 0,
                    name = "Ремонт",
                    emoji = null,
                    isIncome = false
                )
            ),
            onClick = {},
            onDismiss = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}
