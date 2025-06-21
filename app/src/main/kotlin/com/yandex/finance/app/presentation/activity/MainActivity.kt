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
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import com.yandex.finance.R
import com.yandex.finance.app.presentation.navigation.AppNavHost
import com.yandex.finance.app.presentation.viewmodel.MainViewModel
import com.yandex.finance.core.ui.provider.LocalSnackBarHostState
import com.yandex.finance.core.ui.provider.showMessage
import com.yandex.finance.core.ui.provider.showShortMessage
import com.yandex.finance.core.ui.theme.YandexFinanceTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.compose.KoinContext
import timber.log.Timber

class MainActivity : ComponentActivity() {

    private val mainViewModel by inject<MainViewModel>()

    private val snackBarHostState = SnackbarHostState()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        observeNetworkConnectivity(snackBarHostState)

        setContent {
            val navHostController = rememberNavController()

            KoinContext {
                YandexFinanceTheme {
                    CompositionLocalProvider(
                        LocalSnackBarHostState provides snackBarHostState
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
        lifecycleScope.launch(Dispatchers.Main) {
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
