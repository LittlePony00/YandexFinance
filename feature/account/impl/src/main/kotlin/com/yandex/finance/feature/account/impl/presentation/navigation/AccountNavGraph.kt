package com.yandex.finance.feature.account.impl.presentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.yandex.finance.feature.account.api.navigation.AccountFlow
import com.yandex.finance.feature.account.api.navigation.AccountGraph
import com.yandex.finance.feature.account.impl.domain.UiAccountModel
import com.yandex.finance.feature.account.impl.presentation.screen.MyAccountEditScreen
import com.yandex.finance.feature.account.impl.presentation.screen.MyAccountScreen
import com.yandex.finance.feature.account.impl.presentation.viewmodel.MyAccountEditViewModel
import com.yandex.finance.feature.account.impl.presentation.viewmodel.MyAccountViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

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
            val myAccountVM = koinViewModel<MyAccountViewModel>()

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
        ) { backStackEntry ->
            val args = backStackEntry.toRoute<AccountFlow.MyAccountEdit>()
            val uiAccountModel = UiAccountModel(
                id = args.id,
                name = args.name,
                icon = args.icon,
                balance = args.balance,
                currency = args.currencyType
            )

            val myAccountEditVM =
                koinViewModel<MyAccountEditViewModel> { parametersOf(uiAccountModel) }

            MyAccountEditScreen(
                onCancelClick = { navController.navigate(AccountFlow.MyAccount) { popUpTo(0) } },
                myAccountEditVM = myAccountEditVM,
            )
        }
    }
}
