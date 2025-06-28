package com.yandex.finance.app.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.yandex.finance.app.presentation.screen.SplashScreen
import com.yandex.finance.feature.outcome.api.navigation.OutcomeGraph

fun NavGraphBuilder.splashNavGraph(navController: NavController) {

    navigation<SplashGraph>(
        startDestination = SplashFlow.SplashScreen,
        enterTransition = { fadeIn(animationSpec = tween(0)) },
        exitTransition = { fadeOut(animationSpec = tween(0)) }
    ) {

        composable<SplashFlow.SplashScreen> {
            SplashScreen(
                onFinished = {
                    navController.navigate(OutcomeGraph) {
                        popUpTo(0)
                    }
                }
            )
        }
    }
}
