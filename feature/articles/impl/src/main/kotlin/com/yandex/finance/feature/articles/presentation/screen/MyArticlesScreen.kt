package com.yandex.finance.feature.articles.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yandex.finance.core.ui.component.icon.EmojiWrapper
import com.yandex.finance.core.ui.component.icon.Search
import com.yandex.finance.core.ui.component.listitem.ListItem
import com.yandex.finance.core.ui.component.topBar.YandexFinanceTopAppBar
import com.yandex.finance.core.ui.theme.RobotoBodyLargeStyle
import com.yandex.finance.feature.articles.R
import com.yandex.finance.feature.articles.domain.UiArticleModel
import com.yandex.finance.feature.articles.presentation.viewmodel.MyArticlesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyArticlesScreen(
    modifier: Modifier = Modifier,
    myArticlesVM: MyArticlesViewModel = koinViewModel(),
) {
    val uiState by myArticlesVM.uiState.collectAsStateWithLifecycle()
    val searchQuery by myArticlesVM.searchQuery.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            YandexFinanceTopAppBar(
                title = {
                    Text(text = stringResource(R.string.my_articles))
                },
            )
        }
    ) { innerPadding ->
        when (val state = uiState) {
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
                    Text(text = "Ошибка загрузки данных")
                }
            }

            is MyArticlesViewModel.State.Content -> {
                val articles by state.articles.collectAsStateWithLifecycle()
                
                MyArticlesScreenContent(
                    modifier = modifier.padding(innerPadding),
                    articles = articles,
                    searchQuery = searchQuery,
                    onSearchQueryChanged = myArticlesVM::onSearchQueryChanged,
                )
            }
        }
    }
}

@Composable
private fun MyArticlesScreenContent(
    articles: List<UiArticleModel>,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        item {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                value = searchQuery,
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
                onValueChange = onSearchQueryChanged,
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
        
        if (articles.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Категории не найдены",
                        style = RobotoBodyLargeStyle,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            items(articles) { article ->
                ListItem(
                    content = {
                        Text(text = article.title)
                    },
                    navigationContent = {
                        EmojiWrapper(
                            text = article.title,
                            emoji = article.emoji
                        )
                    }
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            }
        }
    }
}
