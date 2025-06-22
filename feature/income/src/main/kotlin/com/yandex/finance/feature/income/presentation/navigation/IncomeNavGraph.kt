package com.yandex.finance.feature.income.presentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.yandex.finance.core.navigation.income.IncomeFlow
import com.yandex.finance.core.navigation.income.IncomeGraph
import com.yandex.finance.feature.income.presentation.screen.IncomeTodayScreen
import com.yandex.finance.feature.income.presentation.screen.MyHistoryScreen
import com.yandex.finance.feature.income.presentation.viewmodel.IncomeTodayViewModel
import com.yandex.finance.feature.income.presentation.viewmodel.MyHistoryViewModel
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
            val myHistoryViewModel = koinViewModel<MyHistoryViewModel>()

            MyHistoryScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onAnalysClick = {

                },
                myHistoryVM = myHistoryViewModel
            )
        }
    }
}
