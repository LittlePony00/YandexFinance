package com.yandex.finance.feature

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.yandex.finance.feature.income.presentation.navigation.IncomeGraph
import com.yandex.finance.feature.outcome.presentation.navigation.OutcomeGraph

fun NavGraphBuilder.splashNavGraph(navController: NavController) {

    navigation<SplashGraph>(
        startDestination = SplashFlow.SplashScreen,
        enterTransition = { fadeIn(animationSpec = tween(0)) },
        exitTransition = { fadeOut(animationSpec = tween(0)) }
    ) {

        composable<SplashFlow.SplashScreen> {
            SplashScreen(
                onFinished = {
                    navController.navigate(OutcomeGraph)
                }
            )
        }
    }
}
