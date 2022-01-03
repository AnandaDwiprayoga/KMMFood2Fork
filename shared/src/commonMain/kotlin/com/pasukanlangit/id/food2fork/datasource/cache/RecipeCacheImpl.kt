package com.pasukanlangit.id.food2fork.datasource.cache

import com.pasukanlangit.id.food2fork.datasource.cache.utils.convertIngredientsListToString
import com.pasukanlangit.id.food2fork.datasource.cache.utils.toListRecipe
import com.pasukanlangit.id.food2fork.datasource.cache.utils.toRecipe
import com.pasukanlangit.id.food2fork.domain.model.Recipe
import com.pasukanlangit.id.food2fork.domain.util.DateTimeUtil

class RecipeCacheImpl(
    recipeDatabase: RecipeDatabase,
    private val dateTimeUtil: DateTimeUtil
): RecipeCache {

    private val queries: RecipeDBQueries = recipeDatabase.recipeDBQueries

    override fun insert(recipe: Recipe) {
        queries.insertRecipe(
            id = recipe.id.toLong(),
            title = recipe.title,
            publisher = recipe.publisher,
            featured_image = recipe.featuredImage,
            rating = recipe.rating.toLong(),
            source_url = recipe.sourceUrl,
            ingredients = recipe.ingredients.convertIngredientsListToString(),
            date_updated = dateTimeUtil.toEpochMilliseconds(recipe.dateUpdated),
            date_added = dateTimeUtil.toEpochMilliseconds(recipe.dateAdded)
        )
    }

    override fun insert(recipes: List<Recipe>) {
        for(recipe in recipes){
            insert(recipe)
        }
    }

    override fun search(query: String, page: Int): List<Recipe> {
        return queries.searchRecipes(
            query = query,
            pageSize = RECIPE_PAGINATION_PAGE_SIZE,
            offset = ((page-1)* RECIPE_PAGINATION_PAGE_SIZE)
        ).executeAsList().toListRecipe()
    }

    override fun getAll(page: Int): List<Recipe> {
        return queries.getAllRecipes(
            pageSize = RECIPE_PAGINATION_PAGE_SIZE,
            offset = ((page-1)* RECIPE_PAGINATION_PAGE_SIZE)
        ).executeAsList().toListRecipe()
    }

    override fun get(recipeId: Int): Recipe? {
        return queries.getRecipeById(recipeId.toLong()).executeAsOneOrNull()?.toRecipe()
    }


    companion object {
        const val RECIPE_PAGINATION_PAGE_SIZE = 30L

    }
}