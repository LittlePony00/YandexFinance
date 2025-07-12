package com.yandex.finance.feature.account.impl.presentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.yandex.finance.core.ui.provider.LocalViewModelFactory
import com.yandex.finance.feature.account.api.navigation.AccountFlow
import com.yandex.finance.feature.account.api.navigation.AccountGraph
import com.yandex.finance.feature.account.impl.presentation.screen.MyAccountEditScreen
import com.yandex.finance.feature.account.impl.presentation.screen.MyAccountScreen
import com.yandex.finance.feature.account.impl.presentation.viewmodel.MyAccountEditViewModel
import com.yandex.finance.feature.account.impl.presentation.viewmodel.MyAccountViewModel

fun NavGraphBuilder.accountGraph(navController: NavController) {

    navigation<AccountGraph>(
        startDestination = AccountFlow.MyAccount,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() }
    ) {

        composable<AccountFlow.MyAccount>(
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            val myAccountVM: MyAccountViewModel =
                viewModel(factory = LocalViewModelFactory.current)

            MyAccountScreen(
                myAccountVM = myAccountVM,
                onEditClick = { uiAccountModel ->
                    navController.navigate(
                        AccountFlow.MyAccountEdit(
                            id = uiAccountModel.id,
                            icon = uiAccountModel.icon,
                            name = uiAccountModel.name,
                            balance = uiAccountModel.balance,
                            currencyType = uiAccountModel.currency
                        )
                    ) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable<AccountFlow.MyAccountEdit>(
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            val myAccountEditVM: MyAccountEditViewModel =
                viewModel(factory = LocalViewModelFactory.current)

            MyAccountEditScreen(
                onCancelClick = { navController.navigate(AccountFlow.MyAccount) { popUpTo(0) } },
                myAccountEditVM = myAccountEditVM,
            )
        }
    }
}
