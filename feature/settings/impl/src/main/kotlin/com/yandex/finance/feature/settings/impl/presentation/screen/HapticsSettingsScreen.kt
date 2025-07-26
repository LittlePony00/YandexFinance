package com.yandex.finance.feature.settings.impl.presentation.screen

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yandex.finance.core.datastore.SettingsDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import androidx.compose.ui.res.stringResource
import com.yandex.finance.core.ui.component.topBar.YandexFinanceTopAppBar
import com.yandex.finance.feature.settings.impl.R

val vibrationEffects = listOf("Click", "Double Click", "Heavy", "Light")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HapticsSettingsScreen(
    onBack: () -> Unit,
    app: Application,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    var hapticsEnabled by remember { mutableStateOf(false) }
    var selectedEffect by remember { mutableStateOf(vibrationEffects[0]) }
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        hapticsEnabled = SettingsDataStore.isHapticsEnabled(app).first()
        SettingsDataStore.hapticsEffect(app).first()?.let {
            if (it in vibrationEffects) selectedEffect = it
        }
    }

    Scaffold(
        topBar = {
            YandexFinanceTopAppBar(
                title = {
                    Text(text = stringResource(R.string.haptics))
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(R.string.vibration), style = MaterialTheme.typography.bodyLarge)
                Switch(
                    checked = hapticsEnabled,
                    onCheckedChange = {
                        hapticsEnabled = it
                        scope.launch {
                            SettingsDataStore.setHapticsEnabled(app, it)
                        }
                    }
                )
            }
            if (hapticsEnabled) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    Text(
                        text = stringResource(R.string.effect, selectedEffect),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable, true),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        vibrationEffects.forEach { effect ->
                            DropdownMenuItem(
                                text = { Text(effect) },
                                onClick = {
                                    selectedEffect = effect
                                    expanded = false
                                    scope.launch {
                                        SettingsDataStore.setHapticsEffect(app, effect)
                                    }
                                }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.haptics_description),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
} 