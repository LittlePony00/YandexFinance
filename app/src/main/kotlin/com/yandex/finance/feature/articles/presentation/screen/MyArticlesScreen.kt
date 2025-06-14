package com.yandex.finance.feature.articles.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yandex.finance.R
import com.yandex.finance.core.ui.component.icon.Search
import com.yandex.finance.core.ui.component.listitem.ListItem
import com.yandex.finance.core.ui.component.topBar.YandexFinanceTopAppBar
import com.yandex.finance.core.ui.theme.RobotoBodyLargeStyle
import com.yandex.finance.feature.articles.domain.UiArticleModel
import com.yandex.finance.feature.articles.presentation.viewmodel.MyArticlesViewModel
import io.github.composegears.valkyrie.TestIcon

@Composable
fun MyArticlesScreen(
    modifier: Modifier = Modifier,
    myArticlesVM: MyArticlesViewModel,
) {
    val uiState = myArticlesVM.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            YandexFinanceTopAppBar(
                title = {
                    Text(text = stringResource(R.string.my_articles))
                },
            )
        }
    ) { innerPadding ->
        when (val state = uiState.value) {
            is MyArticlesViewModel.State.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onSurface)
                }
            }

            is MyArticlesViewModel.State.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Error") // Пока так
                }
            }

            is MyArticlesViewModel.State.Content -> {
                val myArticlesUiState = state.myArticlesUiState.collectAsStateWithLifecycle()

                MyArticlesScreenContent(
                    modifier = modifier.padding(innerPadding),
                    myArticlesUiState = myArticlesUiState
                )
            }
        }
    }
}

@Composable
private fun MyArticlesScreenContent(
    myArticlesUiState: State<UiArticleModel>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        item {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                value = "",
                placeholder = {
                    Text(
                        text = "Найти статью",
                        style = RobotoBodyLargeStyle,
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    focusedIndicatorColor = MaterialTheme.colorScheme.outlineVariant,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.outlineVariant,
                ),
                onValueChange = {},
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
        items(5) {
            ListItem(
                onClick = {},
                content = {
                    Text(text = "Аренда квартиры")
                },
                navigationContent = {
                    Image(
                        imageVector = Icons.TestIcon,
                        contentDescription = Icons.TestIcon.name,
                    )
                }
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        }
    }
}
