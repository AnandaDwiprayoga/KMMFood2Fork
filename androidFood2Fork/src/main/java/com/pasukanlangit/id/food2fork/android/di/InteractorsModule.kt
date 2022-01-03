package com.pasukanlangit.id.food2fork.android.di

import com.pasukanlangit.id.food2fork.datasource.cache.RecipeCache
import com.pasukanlangit.id.food2fork.datasource.network.RecipeService
import com.pasukanlangit.id.food2fork.interactors.recipeDetail.GetRecipe
import com.pasukanlangit.id.food2fork.interactors.recipeList.SearchRecipes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InteractorsModule {

    @Provides
    @Singleton
    fun provideSearchRecipeInteractor(recipeService: RecipeService, recipeCache: RecipeCache): SearchRecipes =
        SearchRecipes(
            recipeService = recipeService,
            recipeCache = recipeCache
        )

    @Provides
    @Singleton
    fun provideGetRecipeInteractor(recipeCache: RecipeCache): GetRecipe =
        GetRecipe(
            recipeCache = recipeCache
        )

}