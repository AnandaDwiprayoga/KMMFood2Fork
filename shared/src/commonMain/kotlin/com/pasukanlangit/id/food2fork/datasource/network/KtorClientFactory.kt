package com.pasukanlangit.id.food2fork.datasource.network

import com.pasukanlangit.id.food2fork.datasource.network.dto.RecipeItemDto
import com.pasukanlangit.id.food2fork.domain.model.Recipe
import com.pasukanlangit.id.food2fork.domain.util.DateTimeUtil
import io.ktor.client.*

expect class KtorClientFactory() {
    fun build(): HttpClient
}

fun RecipeItemDto.toRecipe(): Recipe{
    val dateTimeUtil = DateTimeUtil()
    return Recipe(
        id = pk,
        title = title,
        featuredImage = featuredImage,
        rating = rating,
        publisher = publisher,
        sourceUrl = sourceUrl,
        ingredients = ingredients,
        dateAdded = dateTimeUtil.toLocalDate(longDateAdded.toDouble()),
        dateUpdated = dateTimeUtil.toLocalDate(longDateUpdated.toDouble()),
    )
}

fun List<RecipeItemDto>.toRecipeList(): List<Recipe> =
    this.map { it.toRecipe() }