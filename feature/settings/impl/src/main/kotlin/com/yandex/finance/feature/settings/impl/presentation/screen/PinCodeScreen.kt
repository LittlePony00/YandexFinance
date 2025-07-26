package com.yandex.finance.feature.settings.impl.presentation.screen

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.yandex.finance.core.datastore.PinCodeStorage
import androidx.compose.ui.res.stringResource
import com.yandex.finance.core.ui.component.topBar.YandexFinanceTopAppBar
import com.yandex.finance.feature.settings.impl.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PinCodeScreen(
    onPinSet: () -> Unit,
    app: Application,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    var pin1 by remember { mutableStateOf("") }
    var pin2 by remember { mutableStateOf("") }
    var step by remember { mutableStateOf(1) }

    Scaffold(
        topBar = {
            YandexFinanceTopAppBar(
                title = {
                    Text(text = stringResource(R.string.set_pin_code))
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
            if (step == 1) {
                Text(text = stringResource(R.string.enter_pin_code))
                OutlinedTextField(
                    value = pin1,
                    onValueChange = {
                        if (it.length <= 4 && it.all { c -> c.isDigit() }) pin1 = it
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Button(
                    onClick = {
                        if (pin1.length == 4) {
                            step = 2
                        }
                    },
                    enabled = pin1.length == 4,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.next))
                }
            } else {
                Text(text = stringResource(R.string.repeat_pin_code))
                OutlinedTextField(
                    value = pin2,
                    onValueChange = {
                        if (it.length <= 4 && it.all { c -> c.isDigit() }) pin2 = it
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Button(
                    onClick = {
                        if (pin2 == pin1) {
                            scope.launch {
                                PinCodeStorage.setPinCode(app, pin1)
                                onPinSet()
                            }
                        }
                    },
                    enabled = pin2.length == 4,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.save))
                }
            }
        }
    }
} 