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

            SettingsScreen(settingsVM = settingsVM)
        }
    }
}
