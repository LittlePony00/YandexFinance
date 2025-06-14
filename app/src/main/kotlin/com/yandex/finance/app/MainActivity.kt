package com.yandex.finance.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.yandex.finance.core.ui.theme.YandexFinanceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val navHostController = rememberNavController()

            YandexFinanceTheme {
                AppNavHost(
                    modifier = Modifier.fillMaxSize(),
                    navHostController = navHostController
                )
            }
        }
    }
}
