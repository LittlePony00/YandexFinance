package com.yandex.finance.income.impl.presentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.yandex.finance.core.domain.model.CurrencyType
import com.yandex.finance.core.ui.provider.LocalViewModelFactory
import com.yandex.finance.feature.income.api.navigation.IncomeFlow
import com.yandex.finance.feature.income.api.navigation.IncomeGraph
import com.yandex.finance.feature.transaction_edit.api.navigation.TransactionEditFlow
import com.yandex.finance.feature.transaction_analysis.api.navigation.TransactionAnalysisFlow
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
                onIncomeClick = { transaction ->
                    navController.navigate(
                        TransactionEditFlow.EditTransaction(
                            transactionId = transaction.id,
                            amount = transaction.amount,
                            isEdit = true,
                            isIncome = true,
                            description = transaction.comment ?: "",
                            currencyType = transaction.account.currency
                        )
                    )
                },
                onFabButtonClick = {
                    navController.navigate(
                        TransactionEditFlow.EditTransaction(
                            transactionId = 0,
                            amount = "0.0",
                            isEdit = false,
                            isIncome = true,
                            description = "",
                            currencyType = CurrencyType.RUB
                        )
                    )
                },
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
                    navController.navigate(
                        TransactionAnalysisFlow.Analysis(
                            isIncome = true,
                            startDate = myHistoryViewModel.uiState.value.let { state ->
                                if (state is com.yandex.finance.income.impl.presentation.viewmodel.MyHistoryIncomeViewModel.State.Content) {
                                    state.myHistoryUiState.value.startDate
                                } else ""
                            },
                            endDate = myHistoryViewModel.uiState.value.let { state ->
                                if (state is com.yandex.finance.income.impl.presentation.viewmodel.MyHistoryIncomeViewModel.State.Content) {
                                    state.myHistoryUiState.value.endDate
                                } else ""
                            }
                        )
                    )
                },
                onItemClick = { transaction ->
                    navController.navigate(
                        TransactionEditFlow.EditTransaction(
                            transactionId = transaction.id,
                            amount = transaction.amount,
                            isEdit = true,
                            isIncome = true,
                            description = transaction.comment ?: "",
                            currencyType = transaction.account.currency
                        )
                    )
                },
                myHistoryVM = myHistoryViewModel
            )
        }
    }
}
