package com.yandex.finance.feature.income.presentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.yandex.finance.core.navigation.income.IncomeFlow
import com.yandex.finance.core.navigation.income.IncomeGraph
import com.yandex.finance.core.ui.component.navBar.navigationBarEnterTransition
import com.yandex.finance.core.ui.component.navBar.navigationBarExitTransition
import com.yandex.finance.feature.income.presentation.screen.IncomeTodayScreen
import com.yandex.finance.feature.income.presentation.screen.MyHistoryScreen
import com.yandex.finance.feature.income.presentation.viewmodel.IncomeTodayViewModel
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.incomeNavGraph(navController: NavController) {

    navigation<IncomeGraph>(
        startDestination = IncomeFlow.IncomeToday,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() }
    ) {

        composable<IncomeFlow.IncomeToday>(
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            val incomeTodayVM = koinViewModel<IncomeTodayViewModel>()

            IncomeTodayScreen(
                incomeTodayVM = incomeTodayVM,
                onHistoryClick = {
                    navController.navigate(IncomeFlow.MyHistory)
                }
            )
        }

        composable<IncomeFlow.MyHistory>(
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            MyHistoryScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onAnalysClick = {

                }
            )
        }
    }
}
