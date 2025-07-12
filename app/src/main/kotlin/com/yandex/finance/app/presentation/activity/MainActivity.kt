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

        // Inject dependencies
        appComponent.inject(this)
        
        // Create ViewModel with factory
        val factory =  appComponent.viewModelFactory()
        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]

        observeNetworkConnectivity(snackBarHostState)

        setContent {
            val navHostController = rememberNavController()

            YandexFinanceTheme {
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
