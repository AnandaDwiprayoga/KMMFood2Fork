package com.pasukanlangit.id.food2fork.datasource.cache

import com.pasukanlangit.id.food2fork.domain.model.Recipe

interface RecipeCache {
    fun insert(recipe: Recipe)
    fun insert(recipes: List<Recipe>)
    fun search(query: String, page: Int): List<Recipe>
    fun getAll(page: Int): List<Recipe>
    @Throws(NullPointerException::class)
    fun get(recipeId: Int): Recipe?
}