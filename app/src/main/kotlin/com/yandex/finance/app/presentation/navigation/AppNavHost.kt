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
import com.yandex.finance.app.presentation.navigation.navBar.NavigationBarItem
import com.yandex.finance.app.presentation.navigation.navBar.YandexFinanceNavigationBar
import com.yandex.finance.core.ui.provider.LocalSnackBarHostState
import com.yandex.finance.feature.account.api.navigation.AccountFlow
import com.yandex.finance.feature.account.impl.presentation.navigation.accountGraph
import com.yandex.finance.feature.articles.api.navigation.ArticlesFlow
import com.yandex.finance.feature.articles.presentation.navigation.articlesGraph
import com.yandex.finance.feature.income.api.navigation.IncomeFlow
import com.yandex.finance.feature.outcome.api.navigation.OutcomeFlow
import com.yandex.finance.feature.settings.api.navigation.SettingsFlow
import com.yandex.finance.feature.settings.impl.presentation.navigation.settingsNavGraph
import com.yandex.finance.feature.transaction_edit.impl.presentation.navigation.transactionEditGraph
import com.yandex.finance.feature.transaction_analysis.impl.presentation.navigation.transactionAnalysisGraph
import com.yandex.finance.income.impl.presentation.navigation.incomeNavGraph
import com.yandex.finance.outcome.impl.presentation.navigation.outcomeGraph

private val navigationBarItems = listOf(
    NavigationBarItem.OutcomeToday,
    NavigationBarItem.IncomeToday,
    NavigationBarItem.Account,
    NavigationBarItem.Articles,
    NavigationBarItem.Settings,
)

private val screensWithNavBar = listOf(
    IncomeFlow.IncomeToday,
    OutcomeFlow.OutcomeToday,
    AccountFlow.MyAccount,
    ArticlesFlow.MyArticles,
    SettingsFlow.Settings
)

@Composable
fun AppNavHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    val snackBarHostState = LocalSnackBarHostState.current
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()

    val shouldShowNavBar = navBackStackEntry?.destination?.hierarchy?.any { hierarchy ->
        screensWithNavBar.any { screen -> hierarchy.hasRoute(screen::class) }
    } == true

    val shouldShowSnackBar = navBackStackEntry?.destination?.hierarchy?.any { hierarchy ->
        hierarchy.hasRoute(SplashGraph::class)
    } == false

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (shouldShowNavBar) {
                YandexFinanceNavigationBar(
                    navigationBackStackEntry = navBackStackEntry,
                    onNavBarItemClick = { item ->
                        navHostController.navigate(item) {
                            popUpTo(0) {
                                inclusive = false
                                saveState = false
                            }
                            launchSingleTop = true
                            restoreState = false
                        }
                    },
                    navigationBarItems = navigationBarItems
                )
            }
        },
        snackbarHost = {
            if (shouldShowSnackBar) {
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
            transactionEditGraph(navController = navHostController)
            transactionAnalysisGraph(navController = navHostController)
        }
    }
}
