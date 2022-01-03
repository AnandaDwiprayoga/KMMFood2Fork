package com.pasukanlangit.id.food2fork.presentations.recipedetail


sealed class RecipeDetailEvents {
    data class GetRecipe(val recipeId: Int): RecipeDetailEvents()
    object removeHeadMessageQueue: RecipeDetailEvents()
}
