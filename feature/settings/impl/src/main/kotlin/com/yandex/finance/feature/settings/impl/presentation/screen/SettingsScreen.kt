package com.yandex.finance.feature.settings.impl.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yandex.finance.core.ui.component.listitem.ListItem
import com.yandex.finance.core.ui.component.topBar.YandexFinanceTopAppBar
import com.yandex.finance.feature.settings.impl.R
import com.yandex.finance.feature.settings.impl.domain.UiSettingsModel
import com.yandex.finance.feature.settings.impl.presentation.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    settingsVM: SettingsViewModel
) {
    val uiState = settingsVM.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            YandexFinanceTopAppBar(
                title = {
                    Text(text = stringResource(R.string.settings))
                },
            )
        }
    ) { innerPadding ->
        when (val state = uiState.value) {
            is SettingsViewModel.State.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onSurface)
                }
            }

            is SettingsViewModel.State.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Error") // Пока так
                }
            }

            is SettingsViewModel.State.Content -> {
                val settingsUiState = state.settingsUiState.collectAsStateWithLifecycle()

                SettingsScreenContent(
                    modifier = modifier.padding(innerPadding),
                    settingsData = settingsUiState,
                    onChange = {
                        settingsVM.changeTheme(it)
                    },
                )
            }
        }
    }
}

@Composable
private fun SettingsScreenContent(
    modifier: Modifier = Modifier,
    settingsData: State<UiSettingsModel>,
    onChange: (Boolean) -> Unit,
) {

    Column(modifier = modifier.fillMaxSize()) {
        ListItem(
            modifier = Modifier.height(56.dp),
            content = {
                Text(text = "Тёмная тема")
            },
            contentPaddings = PaddingValues(
                vertical = 16.dp,
                horizontal = 16.dp
            ),
            trailingContent = {
                Switch(
                    modifier = Modifier.height(32.dp),
                    checked = settingsData.value.isDarkMode,
                    onCheckedChange = onChange
                )
            }
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        settingsData.value.chapters.forEach { chapter ->
            ListItem(
                onClick = {},
                contentPaddings = PaddingValues(
                    vertical = 16.dp,
                    horizontal = 16.dp
                ),
                content = {
                    Text(text = chapter.title)
                },
                trailingContent = {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .rotate(-90f),
                        imageVector = Icons.Default.ArrowDropDown,
                        tint = MaterialTheme.colorScheme.surfaceVariant,
                        contentDescription = null
                    )
                }
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        }
    }
}
