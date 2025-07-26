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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.yandex.finance.core.ui.component.button.PrimaryButton
import com.yandex.finance.core.ui.component.listitem.ListItem
import com.yandex.finance.core.ui.component.topBar.YandexFinanceTopAppBar
import com.yandex.finance.core.ui.theme.RobotoBodyLargeStyle
import com.yandex.finance.feature.settings.impl.R
import com.yandex.finance.feature.settings.impl.domain.UiSettingsModel
import com.yandex.finance.feature.settings.impl.presentation.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onColorClick: () -> Unit,
    onHapticsClick: () -> Unit,
    onPinClick: () -> Unit,
    onSyncClick: () -> Unit,
    onLanguageClick: () -> Unit,
    settingsVM: SettingsViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState = settingsVM.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            YandexFinanceTopAppBar(
                title = {
                    Text(text = stringResource(R.string.settings))
                }
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
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = stringResource(R.string.error),
                            style = RobotoBodyLargeStyle
                        )
                        PrimaryButton(
                            text = stringResource(com.yandex.finance.core.ui.R.string.retry_text),
                            onButtonClick = {
                                state.retry.invoke()
                            }
                        )
                    }
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
                    onColorClick = onColorClick,
                    onHapticsClick = onHapticsClick,
                    onPinClick = onPinClick,
                    onSyncClick = onSyncClick,
                    onLanguageClick = onLanguageClick
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
    onColorClick: () -> Unit,
    onHapticsClick: () -> Unit,
    onPinClick: () -> Unit,
    onSyncClick: () -> Unit,
    onLanguageClick: () -> Unit
) {

    Column(modifier = modifier.fillMaxSize()) {
        ListItem(
            modifier = Modifier.height(56.dp),
            content = {
                Text(text = stringResource(R.string.dark_theme))
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
        ListItem(
            onClick = onColorClick,
            contentPaddings = PaddingValues(
                vertical = 16.dp,
                horizontal = 16.dp
            ),
            content = {
                Text(text = stringResource(R.string.main_color))
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
        ListItem(
            onClick = onHapticsClick,
            contentPaddings = PaddingValues(
                vertical = 16.dp,
                horizontal = 16.dp
            ),
            content = {
                Text(text = stringResource(R.string.haptics))
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
        ListItem(
            onClick = onPinClick,
            contentPaddings = PaddingValues(
                vertical = 16.dp,
                horizontal = 16.dp
            ),
            content = {
                Text(text = stringResource(R.string.pin_code))
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
        ListItem(
            onClick = onSyncClick,
            contentPaddings = PaddingValues(
                vertical = 16.dp,
                horizontal = 16.dp
            ),
            content = {
                Text(text = stringResource(R.string.sync))
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
        ListItem(
            onClick = onLanguageClick,
            contentPaddings = PaddingValues(
                vertical = 16.dp,
                horizontal = 16.dp
            ),
            content = {
                Text(text = stringResource(R.string.language))
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
        Text(
            text = stringResource(R.string.last_sync, settingsData.value.lastSyncTime),
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
