package com.yandex.finance.feature.settings.presentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.yandex.finance.core.navigation.settings.SettingsFlow
import com.yandex.finance.core.navigation.settings.SettingsGraph
import com.yandex.finance.core.ui.component.navBar.navigationBarEnterTransition
import com.yandex.finance.core.ui.component.navBar.navigationBarExitTransition
import com.yandex.finance.feature.settings.presentation.screen.SettingsScreen
import com.yandex.finance.feature.settings.presentation.viewmodel.SettingsViewModel

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
            val settingsVM = viewModel<SettingsViewModel>()

            SettingsScreen(settingsVM = settingsVM)
        }
    }
}
