package com.pasukanlangit.id.food2fork.datasource.di

import com.pasukanlangit.id.food2fork.datasource.network.KtorClientFactory
import com.pasukanlangit.id.food2fork.datasource.network.RecipeService
import com.pasukanlangit.id.food2fork.datasource.network.RecipeServiceImpl

class NetworkModule {
    val recipeService: RecipeService by lazy {
        RecipeServiceImpl(
            httpClient = KtorClientFactory().build(),
            baseUrl = RecipeServiceImpl.BASE_URL
        )
    }
}