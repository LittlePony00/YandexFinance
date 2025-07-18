package com.yandex.finance.app.presentation.navigation.navBar

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector
import com.yandex.finance.core.ui.navigation.Graph
import com.yandex.finance.feature.account.api.navigation.AccountGraph
import com.yandex.finance.feature.articles.api.navigation.ArticlesGraph
import com.yandex.finance.feature.income.api.navigation.IncomeGraph
import com.yandex.finance.feature.outcome.api.navigation.OutcomeGraph
import com.yandex.finance.feature.settings.api.navigation.SettingsGraph
import com.yandex.finance.core.ui.R
import com.yandex.finance.core.ui.component.icon.Articles
import com.yandex.finance.core.ui.component.icon.Check
import com.yandex.finance.core.ui.component.icon.Income
import com.yandex.finance.core.ui.component.icon.Outcome
import com.yandex.finance.core.ui.component.icon.Settings

sealed class NavigationBarItem<out T : Graph>(
    val route: T,
    val icon: ImageVector,
    @StringRes val title: Int,
) {

    data object IncomeToday : NavigationBarItem<IncomeGraph>(
        icon = Icons.Income,
        route = IncomeGraph,
        title = R.string.income,
    )

    data object OutcomeToday : NavigationBarItem<OutcomeGraph>(
        icon = Icons.Outcome,
        route = OutcomeGraph,
        title = R.string.outcome,
    )

    data object Articles : NavigationBarItem<ArticlesGraph>(
        icon = Icons.Articles,
        route = ArticlesGraph,
        title = R.string.articles,
    )

    data object Account : NavigationBarItem<AccountGraph>(
        icon = Icons.Check,
        route = AccountGraph,
        title = R.string.account,
    )

    data object Settings : NavigationBarItem<SettingsGraph>(
        icon = Icons.Settings,
        route = SettingsGraph,
        title = R.string.settings
    )
}
