package com.pasukanlangit.id.food2fork.datasource.di

import com.pasukanlangit.id.food2fork.interactors.recipeList.SearchRecipes

class SearchRecipesModule(
    private val networkModule: NetworkModule,
    private val cacheModule: CacheModule
) {
    val searchRecipes: SearchRecipes by lazy {
        SearchRecipes(
            recipeService = networkModule.recipeService,
            recipeCache = cacheModule.recipeCache
        )
    }
}