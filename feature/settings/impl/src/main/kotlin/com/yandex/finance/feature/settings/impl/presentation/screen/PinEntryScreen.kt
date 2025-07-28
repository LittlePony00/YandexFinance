package com.yandex.finance.feature.settings.impl.presentation.screen

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.yandex.finance.core.datastore.PinCodeStorage
import androidx.compose.ui.res.stringResource
import com.yandex.finance.core.ui.component.topBar.YandexFinanceTopAppBar
import com.yandex.finance.feature.settings.impl.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PinEntryScreen(
    app: Application,
    onPinSuccess: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var pin by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            YandexFinanceTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.enter_pin),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(16.dp)
                    )
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
            OutlinedTextField(
                value = pin,
                onValueChange = {
                    if (it.length <= 4 && it.all { c -> c.isDigit() }) pin = it
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Button(
                onClick = {
                    scope.launch {
                        val realPin = PinCodeStorage.getPinCode(app)
                        if (pin == realPin) {
                            onPinSuccess()
                        }
                    }
                },
                enabled = pin.length == 4,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.login))
            }
            if (error != null) {
                Text(text = error!!, color = MaterialTheme.colorScheme.error)
            }
        }
    }
} 