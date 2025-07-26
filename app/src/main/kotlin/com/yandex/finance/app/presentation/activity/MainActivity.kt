package com.yandex.finance.app.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import com.yandex.finance.R
import com.yandex.finance.app.presentation.navigation.AppNavHost
import com.yandex.finance.app.presentation.viewmodel.MainViewModel
import com.yandex.finance.appComponent
import com.yandex.finance.core.common.HasDependencies
import com.yandex.finance.core.ui.provider.LocalSnackBarHostState
import com.yandex.finance.core.ui.provider.LocalViewModelFactory
import com.yandex.finance.core.ui.provider.showMessage
import com.yandex.finance.core.ui.provider.showShortMessage
import com.yandex.finance.core.ui.theme.YandexFinanceTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.yandex.finance.core.datastore.SettingsDataStore
import androidx.compose.runtime.remember
import com.yandex.finance.core.datastore.PinCodeStorage
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.yandex.finance.feature.settings.impl.presentation.screen.PinEntryScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import android.content.res.Configuration
import android.os.Build
import java.util.Locale

class MainActivity : ComponentActivity(), HasDependencies {

    @Inject
    lateinit var mainViewModelFactory: MainViewModel.Factory

    private lateinit var mainViewModel: MainViewModel

    override val depsMap by lazy {
        appComponent.depsMap()
    }

    private val snackBarHostState = SnackbarHostState()



    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        appComponent.inject(this)

        val factory = appComponent.viewModelFactory()
        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]

        observeNetworkConnectivity(snackBarHostState)

        setContent {
            val navHostController = rememberNavController()
            val mainColorFlow = remember { SettingsDataStore.mainColor(applicationContext) }
            val mainColorName by mainColorFlow.collectAsState(initial = null)

            val darkThemeFlow = remember { SettingsDataStore.isDarkTheme(applicationContext) }
            val isDarkTheme by darkThemeFlow.collectAsState(initial = false)

            val localeFlow = remember { SettingsDataStore.locale(applicationContext) }
            val currentLocale by localeFlow.collectAsState(initial = "ru-RU")

            var pinChecked by remember { mutableStateOf(false) }
            var pinRequired by remember { mutableStateOf(false) }
            var pinOk by remember { mutableStateOf(false) }

            runBlocking {
                val pin = PinCodeStorage.getPinCode(applicationContext)
                pinRequired = !pin.isNullOrEmpty()
                pinChecked = true
            }

            var previousLocale by remember { mutableStateOf<String?>(null) }
            
            // Handle locale changes
            LaunchedEffect(currentLocale) {
                if (previousLocale != null && previousLocale != currentLocale) {
                    // Apply locale change
                    val locale = if (currentLocale.contains("-")) {
                        val parts = currentLocale.split("-")
                        Locale(parts[0], parts[1])
                    } else {
                        Locale(currentLocale)
                    }
                    Locale.setDefault(locale)
                    
                    val config = Configuration(resources.configuration)
                    config.setLocale(locale)
                    createConfigurationContext(config)
                    
                    // Recreate activity to apply changes
                    recreate()
                }
                previousLocale = currentLocale
            }

            YandexFinanceTheme(
                darkTheme = isDarkTheme,
                mainColorName = mainColorName
            ) {
                if (pinRequired && !pinOk) {
                    PinEntryScreen(
                        app = this.application,
                        onPinSuccess = { pinOk = true }
                    )
                } else {
                    CompositionLocalProvider(
                        LocalSnackBarHostState provides snackBarHostState,
                        LocalViewModelFactory provides factory
                    ) {
                        AppNavHost(
                            modifier = Modifier.fillMaxSize(),
                            navHostController = navHostController
                        )
                    }
                }
            }
        }
    }

    private fun observeNetworkConnectivity(snackBarHostState: SnackbarHostState) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.isConnected.collectLatest { isConnected ->
                    Timber.d("isConnected: $isConnected, wasDisconnected: ${mainViewModel.wasDisconnected.value}")

                    if (!isConnected) {
                        mainViewModel.changeWasDisconnected(true)
                        val noInternetConnectionText = getString(R.string.no_internet_connection)

                        snackBarHostState.showMessage(message = noInternetConnectionText)
                    } else if (mainViewModel.wasDisconnected.value) {
                        mainViewModel.changeWasDisconnected(false)
                        val internetRestoredText = getString(R.string.internet_connection_restored)

                        snackBarHostState.showShortMessage(message = internetRestoredText)
                    }
                }
            }
        }
    }
}
