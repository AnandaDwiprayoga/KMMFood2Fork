package com.pasukanlangit.id.food2fork.android.presentation.navigation

sealed class Screens(
    val route: String
) {
    object RecipeList: Screens("recipeList")

    object RecipeDetail: Screens("recipeDetail")
}
