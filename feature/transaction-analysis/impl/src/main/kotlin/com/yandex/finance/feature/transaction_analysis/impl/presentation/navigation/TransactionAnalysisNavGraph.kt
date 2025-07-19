package com.yandex.finance.feature.transaction_analysis.impl.presentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.yandex.finance.core.common.findDependencies
import com.yandex.finance.feature.transaction_analysis.api.di.TransactionAnalysisDependencies
import com.yandex.finance.feature.transaction_analysis.api.domain.model.CategoryAnalysisItem
import com.yandex.finance.feature.transaction_analysis.api.navigation.TransactionAnalysisFlow
import com.yandex.finance.feature.transaction_analysis.api.navigation.TransactionAnalysisGraph
import com.yandex.finance.feature.transaction_analysis.impl.presentation.screen.TransactionAnalysisScreen
import com.yandex.finance.feature.transaction_analysis.impl.presentation.viewmodel.TransactionAnalysisViewModel

fun NavGraphBuilder.transactionAnalysisGraph(navController: NavController) {

    navigation<TransactionAnalysisGraph>(
        startDestination = TransactionAnalysisFlow.Analysis::class,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() }
    ) {

        composable<TransactionAnalysisFlow.Analysis>(
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) { backStackEntry ->
            val context = LocalContext.current
            val args = backStackEntry.toRoute<TransactionAnalysisFlow.Analysis>()
            val dependencies = context.findDependencies<TransactionAnalysisDependencies>()
            val savedStateHandle = backStackEntry.savedStateHandle
            
            savedStateHandle["isIncome"] = args.isIncome
            
            val viewModel: TransactionAnalysisViewModel = remember(savedStateHandle) {
                dependencies.transactionAnalysisViewModelFactory(savedStateHandle) as TransactionAnalysisViewModel
            }

            TransactionAnalysisScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onItemClick = { categoryItem ->
                    // TODO: Навигация к детальному просмотру категории
                },
                transactionAnalysisVM = viewModel
            )
        }
    }
} 