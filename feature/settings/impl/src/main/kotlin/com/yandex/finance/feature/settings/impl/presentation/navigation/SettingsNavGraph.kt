package com.yandex.finance.feature.settings.impl.presentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.yandex.finance.feature.settings.api.navigation.SettingsFlow
import com.yandex.finance.feature.settings.api.navigation.SettingsGraph
import com.yandex.finance.feature.settings.impl.presentation.screen.SettingsScreen
import com.yandex.finance.feature.settings.impl.presentation.viewmodel.SettingsViewModel
import com.yandex.finance.core.ui.provider.LocalViewModelFactory
import com.yandex.finance.feature.settings.impl.presentation.screen.ColorPickerScreen
import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.yandex.finance.feature.settings.impl.presentation.screen.HapticsSettingsScreen
import com.yandex.finance.feature.settings.impl.presentation.screen.PinCodeScreen
import com.yandex.finance.feature.settings.impl.presentation.screen.SyncFrequencyScreen
import com.yandex.finance.feature.settings.impl.presentation.screen.LanguageScreen

fun NavGraphBuilder.settingsNavGraph(navController: NavController) {

    navigation<SettingsGraph>(
        startDestination = SettingsFlow.Settings,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() }
    ) {

        composable<SettingsFlow.Settings>(
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            val settingsVM = viewModel<SettingsViewModel>(factory = LocalViewModelFactory.current)
            val context = LocalContext.current.applicationContext as Application
            SettingsScreen(
                onColorClick = { navController.navigate(SettingsFlow.ColorPicker) },
                onHapticsClick = { navController.navigate(SettingsFlow.HapticsSettings) },
                onPinClick = { navController.navigate(SettingsFlow.PinCode) },
                onSyncClick = { navController.navigate(SettingsFlow.SyncFrequency) },
                onLanguageClick = { navController.navigate(SettingsFlow.Language) },
                settingsVM = settingsVM
            )
        }
        composable<SettingsFlow.ColorPicker>(
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            val context = LocalContext.current.applicationContext as Application
            ColorPickerScreen(
                onColorSelected = { navController.popBackStack() },
                app = context
            )
        }
        composable<SettingsFlow.HapticsSettings>(
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            val context = LocalContext.current.applicationContext as Application
            HapticsSettingsScreen(
                onBack = { navController.popBackStack() },
                app = context
            )
        }
        composable<SettingsFlow.PinCode>(
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            val context = LocalContext.current.applicationContext as Application
            PinCodeScreen(
                onPinSet = { navController.popBackStack() },
                app = context
            )
        }
        composable<SettingsFlow.SyncFrequency>(
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            val context = LocalContext.current.applicationContext as Application
            SyncFrequencyScreen(
                onFrequencySet = { navController.popBackStack() },
                app = context
            )
        }
        composable<SettingsFlow.Language>(
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            val context = LocalContext.current.applicationContext as Application
            LanguageScreen(
                onLocaleSet = { navController.popBackStack() },
                app = context
            )
        }
    }
}
