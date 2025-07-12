package com.yandex.finance.income.impl.presentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.yandex.finance.core.ui.provider.LocalViewModelFactory
import com.yandex.finance.feature.income.api.navigation.IncomeFlow
import com.yandex.finance.feature.income.api.navigation.IncomeGraph
import com.yandex.finance.income.impl.presentation.screen.IncomeTodayScreen
import com.yandex.finance.income.impl.presentation.screen.MyHistoryScreen
import com.yandex.finance.income.impl.presentation.viewmodel.IncomeTodayViewModel
import com.yandex.finance.income.impl.presentation.viewmodel.MyHistoryIncomeViewModel

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
            val incomeTodayVM =
                viewModel<IncomeTodayViewModel>(factory = LocalViewModelFactory.current)

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
            val myHistoryViewModel: MyHistoryIncomeViewModel =
                viewModel(factory = LocalViewModelFactory.current)

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
