package com.yandex.finance.feature.outcome.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.yandex.finance.core.ui.component.navBar.navigationBarEnterTransition
import com.yandex.finance.core.ui.component.navBar.navigationBarExitTransition
import com.yandex.finance.feature.outcome.presentation.screen.OutcomeTodayScreen
import com.yandex.finance.feature.outcome.presentation.viewmodel.OutcomeTodayViewModel

fun NavGraphBuilder.outcomeGraph(navController: NavController) {

    navigation<OutcomeGraph>(
        startDestination = OutcomeFlow.OutcomeToday,
        enterTransition = { navigationBarEnterTransition(targetState, initialState) },
        exitTransition = { navigationBarExitTransition(targetState, initialState) }
    ) {

        composable<OutcomeFlow.OutcomeToday> {
            val outcomeTodayVM = viewModel<OutcomeTodayViewModel>()

            OutcomeTodayScreen(outcomeTodayVM = outcomeTodayVM)
        }

        composable<OutcomeFlow.MyOutcome> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "MyOutcome")
            }
        }

        composable<OutcomeFlow.MyHistory> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(text = "MyHistory")
            }
        }
    }
}
