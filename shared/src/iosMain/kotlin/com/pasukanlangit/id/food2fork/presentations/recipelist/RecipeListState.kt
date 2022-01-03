package com.pasukanlangit.id.food2fork.presentations.recipelist

import com.pasukanlangit.id.food2fork.domain.model.GenericMessageInfo
import com.pasukanlangit.id.food2fork.domain.model.Recipe
import com.pasukanlangit.id.food2fork.domain.util.Queue

actual data class RecipeListState(
    val isLoading: Boolean = false,
    val page: Int = 1,
    val query: String = "",
    val selectedCategory: FoodCategory? = null,
    val bottomRecipe: Recipe? = null,
    val errorQueue: Queue<GenericMessageInfo> = Queue(mutableListOf()),
    val recipes: List<Recipe> = listOf()
){
    constructor(): this(
        isLoading = false,
        page = 1,
        query = "",
        selectedCategory = null,
        bottomRecipe = null,
        errorQueue = Queue(mutableListOf()),
        recipes = listOf()
    )

}
