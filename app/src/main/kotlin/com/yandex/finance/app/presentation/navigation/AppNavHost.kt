package com.yandex.finance.app.presentation.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.yandex.finance.core.navigation.splash.SplashGraph
import com.yandex.finance.core.ui.component.navBar.YandexFinanceNavigationBar
import com.yandex.finance.core.ui.provider.LocalSnackBarHostState
import com.yandex.finance.feature.account.presentation.navigation.accountGraph
import com.yandex.finance.feature.articles.presentation.navigation.articlesGraph
import com.yandex.finance.feature.income.presentation.navigation.incomeNavGraph
import com.yandex.finance.feature.outcome.presentation.navigation.outcomeGraph
import com.yandex.finance.feature.settings.presentation.navigation.settingsNavGraph

@Composable
fun AppNavHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    val snackBarHostState = LocalSnackBarHostState.current
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()

    val shouldShowNavBar = navBackStackEntry?.destination?.hierarchy?.any {
        it.hasRoute(SplashGraph::class)
    } == false

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (shouldShowNavBar) {
                YandexFinanceNavigationBar(navHostController)
            }
        },
        snackbarHost = {
            if (shouldShowNavBar) {
                SnackbarHost(snackBarHostState)
            }
        },
        contentWindowInsets = WindowInsets.navigationBars
    ) { innerPaddings ->
        NavHost(
            modifier = modifier
                .padding(innerPaddings)
                .consumeWindowInsets(innerPaddings)
                .imePadding(),
            navController = navHostController,
            startDestination = SplashGraph
        ) {
            splashNavGraph(navController = navHostController)
            incomeNavGraph(navController = navHostController)
            outcomeGraph(navController = navHostController)
            articlesGraph(navController = navHostController)
            accountGraph(navController = navHostController)
            settingsNavGraph(navController = navHostController)
        }
    }
}
