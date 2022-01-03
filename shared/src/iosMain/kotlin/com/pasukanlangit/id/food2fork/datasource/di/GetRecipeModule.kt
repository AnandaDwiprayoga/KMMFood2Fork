package com.pasukanlangit.id.food2fork.datasource.di

import com.pasukanlangit.id.food2fork.interactors.recipeDetail.GetRecipe

class GetRecipeModule(
    private val cacheModule: CacheModule
) {
    val getRecipe: GetRecipe by lazy {
        GetRecipe(
            recipeCache = cacheModule.recipeCache
        )
    }
}