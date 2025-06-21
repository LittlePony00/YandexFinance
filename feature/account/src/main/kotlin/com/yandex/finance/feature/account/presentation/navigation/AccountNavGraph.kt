package com.yandex.finance.feature.account.presentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.yandex.finance.core.navigation.account.AccountFlow
import com.yandex.finance.core.navigation.account.AccountGraph
import com.yandex.finance.feature.account.presentation.screen.MyAccountScreen
import com.yandex.finance.feature.account.presentation.viewmodel.MyAccountViewModel
import org.koin.androidx.compose.koinViewModel

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

            MyAccountScreen(myAccountVM = myAccountVM)
        }
    }
}
