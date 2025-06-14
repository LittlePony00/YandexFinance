package com.yandex.finance.feature.income.presentation.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.yandex.finance.core.ui.component.navBar.navigationBarEnterTransition
import com.yandex.finance.core.ui.component.navBar.navigationBarExitTransition
import com.yandex.finance.feature.income.presentation.screen.IncomeTodayScreen
import com.yandex.finance.feature.income.presentation.viewmodel.IncomeTodayViewModel

fun NavGraphBuilder.incomeNavGraph(navController: NavController) {

    navigation<IncomeGraph>(
        startDestination = IncomeFlow.IncomeToday,
        enterTransition = { navigationBarEnterTransition(targetState, initialState) },
        exitTransition = { navigationBarExitTransition(targetState, initialState) }
    ) {

        composable<IncomeFlow.IncomeToday> {
            val incomeTodayVM = viewModel<IncomeTodayViewModel>()

            IncomeTodayScreen(incomeTodayVM = incomeTodayVM)
        }
    }
}
