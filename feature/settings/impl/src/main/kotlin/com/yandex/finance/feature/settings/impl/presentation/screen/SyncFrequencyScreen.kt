package com.yandex.finance.feature.settings.impl.presentation.screen

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.yandex.finance.core.datastore.SettingsDataStore
import com.yandex.finance.core.data.sync.DataSyncServiceHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.ui.res.stringResource
import com.yandex.finance.core.ui.component.topBar.YandexFinanceTopAppBar
import com.yandex.finance.feature.settings.impl.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SyncFrequencyScreen(
    onFrequencySet: () -> Unit,
    app: Application,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    var frequency by remember { mutableFloatStateOf(12f) }

    LaunchedEffect(Unit) {
        frequency = SettingsDataStore.syncFrequency(app).first().toFloat()
    }

    Scaffold(
        topBar = {
            YandexFinanceTopAppBar(
                title = {
                    Text(text = stringResource(R.string.sync_frequency))
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
            Text(text = stringResource(R.string.select_sync_frequency, frequency.toInt()))
            Slider(
                value = frequency,
                onValueChange = { frequency = it },
                valueRange = 1f..24f,
                steps = 22,
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    scope.launch {
                        SettingsDataStore.setSyncFrequency(app, frequency.toInt())
                        // Reschedule sync with new interval
                        withContext(Dispatchers.IO) {
                            DataSyncServiceHolder.instance?.schedulePeriodicSyncWithInterval()
                        }
                        onFrequencySet()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.save))
            }
        }
    }
} 