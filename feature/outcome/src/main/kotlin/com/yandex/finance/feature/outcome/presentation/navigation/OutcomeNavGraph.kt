package com.yandex.finance.feature.outcome.presentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import com.yandex.finance.core.navigation.outcome.OutcomeFlow
import com.yandex.finance.core.navigation.outcome.OutcomeGraph
import com.yandex.finance.core.ui.component.navBar.navigationBarEnterTransition
import com.yandex.finance.core.ui.component.navBar.navigationBarExitTransition
import com.yandex.finance.feature.outcome.presentation.screen.MyHistoryScreen
import com.yandex.finance.feature.outcome.presentation.screen.OutcomeTodayScreen
import com.yandex.finance.feature.outcome.presentation.viewmodel.OutcomeTodayViewModel
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.outcomeGraph(navController: NavController) {

    navigation<OutcomeGraph>(
        startDestination = OutcomeFlow.OutcomeToday,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() }
    ) {

        composable<OutcomeFlow.OutcomeToday>(
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            val outcomeTodayVM = koinViewModel<OutcomeTodayViewModel>()

            OutcomeTodayScreen(
                outcomeTodayVM = outcomeTodayVM,
                onHistoryClick = {
                    navController.navigate(OutcomeFlow.MyHistory)
                }
            )
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
            MyHistoryScreen(
                onAnalysClick = {},
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
