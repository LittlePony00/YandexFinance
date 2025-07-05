package com.yandex.finance.feature.articles.presentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.yandex.finance.feature.articles.api.navigation.ArticlesFlow
import com.yandex.finance.feature.articles.api.navigation.ArticlesGraph
import com.yandex.finance.feature.articles.presentation.screen.MyArticlesScreen
import com.yandex.finance.feature.articles.presentation.viewmodel.MyArticlesViewModel
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.articlesGraph(navController: NavController) {

    navigation<ArticlesGraph>(
        startDestination = ArticlesFlow.MyArticles,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() }
    ) {

        composable<ArticlesFlow.MyArticles>(
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            val myArticlesVM = koinViewModel<MyArticlesViewModel>()


            MyArticlesScreen(myArticlesVM = myArticlesVM)
        }
    }
}
