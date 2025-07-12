package com.yandex.finance.feature.transaction_edit.impl.presentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.yandex.finance.core.common.findDependencies
import com.yandex.finance.feature.transaction_edit.api.di.TransactionEditDependencies
import com.yandex.finance.feature.transaction_edit.api.navigation.TransactionEditFlow
import com.yandex.finance.feature.transaction_edit.api.navigation.TransactionEditGraph
import com.yandex.finance.feature.transaction_edit.impl.presentation.screen.TransactionEditScreen
import com.yandex.finance.feature.transaction_edit.impl.presentation.viewmodel.TransactionEditViewModel
import timber.log.Timber

fun NavGraphBuilder.transactionEditGraph(navController: NavController) {
    navigation<TransactionEditGraph>(
        startDestination = TransactionEditFlow.EditTransaction::class
    ) {
        composable<TransactionEditFlow.EditTransaction>(
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) { backStackEntry ->
            val context = LocalContext.current
            val args = backStackEntry.toRoute<TransactionEditFlow.EditTransaction>()
            val dependencies = context.findDependencies<TransactionEditDependencies>()
            val savedStateHandle = backStackEntry.savedStateHandle
            
            val viewModel: TransactionEditViewModel = remember(savedStateHandle) {
                dependencies.transactionEditViewModelFactory(savedStateHandle) as TransactionEditViewModel
            }

            TransactionEditScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    Timber.d("onNavigateBack called, calling navController.popBackStack()")
                    navController.popBackStack()
                },
                isEdit = args.isEdit,
                isIncome = args.isIncome,
            )
        }
    }
}
