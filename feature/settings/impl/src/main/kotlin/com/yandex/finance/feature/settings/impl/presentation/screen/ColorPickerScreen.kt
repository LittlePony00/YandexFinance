package com.yandex.finance.feature.settings.impl.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.yandex.finance.core.datastore.SettingsDataStore
import android.app.Application
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.res.stringResource
import com.yandex.finance.core.ui.component.topBar.YandexFinanceTopAppBar
import com.yandex.finance.feature.settings.impl.R

val colorPalette = listOf(
    "Blue" to Color(0xFF2196F3),
    "Green" to Color(0xFF4CAF50),
    "Red" to Color(0xFFF44336),
    "Purple" to Color(0xFF9C27B0),
    "Orange" to Color(0xFFFF9800)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorPickerScreen(
    onColorSelected: () -> Unit,
    app: Application,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            YandexFinanceTopAppBar(
                title = {
                    Text(text = stringResource(R.string.select_main_color))
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            colorPalette.chunked(2).forEach { rowColors ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    rowColors.forEach { (name, color) ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    scope.launch {
                                        SettingsDataStore.setMainColor(app.applicationContext, name)
                                        onColorSelected()
                                    }
                                }
                        ) {
                            Spacer(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(color, shape = MaterialTheme.shapes.medium)
                            )
                            Text(
                                text = name,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
} 