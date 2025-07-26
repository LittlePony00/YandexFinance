package com.yandex.finance.feature.settings.impl.presentation.screen

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.yandex.finance.core.datastore.SettingsDataStore
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.res.stringResource
import com.yandex.finance.core.ui.component.topBar.YandexFinanceTopAppBar
import com.yandex.finance.feature.settings.impl.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageScreen(
    onLocaleSet: () -> Unit,
    app: Application,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val languages = listOf("ru" to "Русский", "en" to "English")
    var selected by remember { mutableStateOf("ru") }

    LaunchedEffect(Unit) {
        selected = SettingsDataStore.locale(app).first()
    }

    Scaffold(
        topBar = {
            YandexFinanceTopAppBar(
                title = {
                    Text(text = stringResource(R.string.language_title))
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
            languages.forEach { (code, label) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selected == code,
                        onClick = { selected = code }
                    )
                    Text(text = label, modifier = Modifier.padding(start = 8.dp))
                }
            }
            Button(
                onClick = {
                    scope.launch {
                        SettingsDataStore.setLocale(app, selected)
                        onLocaleSet()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.save))
            }
        }
    }
} 