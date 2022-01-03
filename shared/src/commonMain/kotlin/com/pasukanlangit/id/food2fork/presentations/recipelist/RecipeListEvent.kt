package com.pasukanlangit.id.food2fork.presentations.recipelist

sealed class RecipeListEvent {
    object LoadRecipes: RecipeListEvent()
    object NextPage: RecipeListEvent()
    data class OnQueryChange(
        val newQuery: String
    ): RecipeListEvent()
    object SubmitSearch: RecipeListEvent()
    data class OnSelectCategory(val category: FoodCategory): RecipeListEvent()
    object removeHeadMessageQueue: RecipeListEvent()
}