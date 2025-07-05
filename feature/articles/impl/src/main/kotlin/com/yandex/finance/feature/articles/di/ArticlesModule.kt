package com.yandex.finance.feature.articles.di

import com.yandex.finance.feature.articles.domain.usecase.GetCategoriesUseCase
import com.yandex.finance.feature.articles.presentation.viewmodel.MyArticlesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val provideArticlesModule = module {

    factory<GetCategoriesUseCase> {
        GetCategoriesUseCase(
            categoryRepository = get()
        )
    }

    viewModel<MyArticlesViewModel> {
        MyArticlesViewModel(
            getCategoriesUseCase = get()
        )
    }
} 