package com.yandex.finance.outcome.impl.presentation.navigation

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
import com.yandex.finance.core.domain.model.CurrencyType
import com.yandex.finance.core.ui.provider.LocalViewModelFactory
import com.yandex.finance.feature.outcome.api.navigation.OutcomeFlow
import com.yandex.finance.feature.outcome.api.navigation.OutcomeGraph
import com.yandex.finance.feature.transaction_edit.api.navigation.TransactionEditFlow
import com.yandex.finance.feature.transaction_analysis.api.navigation.TransactionAnalysisFlow
import com.yandex.finance.outcome.impl.presentation.screen.MyHistoryScreen
import com.yandex.finance.outcome.impl.presentation.screen.OutcomeTodayScreen
import com.yandex.finance.outcome.impl.presentation.viewmodel.MyHistoryOutcomeViewModel
import com.yandex.finance.outcome.impl.presentation.viewmodel.OutcomeTodayViewModel

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
            val factory = LocalViewModelFactory.current
            val outcomeTodayVM = viewModel<OutcomeTodayViewModel>(factory = factory)

            OutcomeTodayScreen(
                outcomeTodayVM = outcomeTodayVM,
                onOutcomeClick = { transaction ->
                    navController.navigate(
                        TransactionEditFlow.EditTransaction(
                            transactionId = transaction.id,
                            amount = transaction.amount,
                            isEdit = true,
                            isIncome = false,
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
                            isIncome = false,
                            description = "",
                            currencyType = CurrencyType.RUB
                        )
                    )
                },
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
            val factory = LocalViewModelFactory.current
            val myHistoryVM = viewModel<MyHistoryOutcomeViewModel>(factory = factory)

            MyHistoryScreen(
                onAnalysClick = {
                    navController.navigate(
                        TransactionAnalysisFlow.Analysis(
                            isIncome = false,
                            startDate = myHistoryVM.uiState.value.let { state ->
                                if (state is com.yandex.finance.outcome.impl.presentation.viewmodel.MyHistoryOutcomeViewModel.State.Content) {
                                    state.myHistoryUiState.value.startDate
                                } else ""
                            },
                            endDate = myHistoryVM.uiState.value.let { state ->
                                if (state is com.yandex.finance.outcome.impl.presentation.viewmodel.MyHistoryOutcomeViewModel.State.Content) {
                                    state.myHistoryUiState.value.endDate
                                } else ""
                            }
                        )
                    )
                },
                onBackClick = {
                    navController.popBackStack()
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
                myHistoryVM = myHistoryVM
            )
        }
    }
}
