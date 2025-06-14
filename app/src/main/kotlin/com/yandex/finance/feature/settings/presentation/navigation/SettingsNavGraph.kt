package com.yandex.finance.feature.settings.presentation.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.yandex.finance.core.ui.component.navBar.navigationBarEnterTransition
import com.yandex.finance.core.ui.component.navBar.navigationBarExitTransition
import com.yandex.finance.feature.settings.presentation.screen.SettingsScreen
import com.yandex.finance.feature.settings.presentation.viewmodel.SettingsViewModel

fun NavGraphBuilder.settingsNavGraph(navController: NavController) {

    navigation<SettingsGraph>(
        startDestination = SettingsFlow.Settings,
        enterTransition = { navigationBarEnterTransition(targetState, initialState) },
        exitTransition = { navigationBarExitTransition(targetState, initialState) }
    ) {

        composable<SettingsFlow.Settings> {
            val settingsVM = viewModel<SettingsViewModel>()

            SettingsScreen(settingsVM = settingsVM)
        }
    }
}
