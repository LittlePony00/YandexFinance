package com.yandex.finance.feature.account.presentation.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.yandex.finance.core.ui.component.navBar.navigationBarEnterTransition
import com.yandex.finance.core.ui.component.navBar.navigationBarExitTransition
import com.yandex.finance.feature.account.presentation.screen.MyAccountScreen
import com.yandex.finance.feature.account.presentation.viewmodel.MyAccountViewModel

fun NavGraphBuilder.accountGraph(navController: NavController) {

    navigation<AccountGraph>(
        startDestination = AccountFlow.MyAccount,
        enterTransition = { navigationBarEnterTransition(targetState, initialState) },
        exitTransition = { navigationBarExitTransition(targetState, initialState) }
    ) {

        composable<AccountFlow.MyAccount> {
            val myAccountVM = viewModel<MyAccountViewModel>()

            MyAccountScreen(myAccountVM = myAccountVM)
        }
    }
}
