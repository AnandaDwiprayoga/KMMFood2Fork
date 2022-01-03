package com.pasukanlangit.id.food2fork.datasource.cache.utils

import com.pasukanlangit.id.food2fork.datasource.cache.Recipe_Entity
import com.pasukanlangit.id.food2fork.domain.model.Recipe
import com.pasukanlangit.id.food2fork.domain.util.DateTimeUtil

val datetimeUtil = DateTimeUtil()

fun Recipe_Entity.toRecipe(): Recipe =
    Recipe(
        id = this.id.toInt(),
        title = this.title,
        publisher = this.publisher,
        featuredImage = this.featured_image,
        rating = this.rating.toInt(),
        sourceUrl = this.source_url,
        ingredients = this.ingredients.convertIngredientsToList(),
        dateAdded = datetimeUtil.toLocalDate(date_added),
        dateUpdated = datetimeUtil.toLocalDate(date_updated)
    )


fun List<Recipe_Entity>.toListRecipe(): List<Recipe> =
    this.map { it.toRecipe() }


fun String.convertIngredientsToList(): List<String>{
    val list = arrayListOf<String>()
    for(ingredient in split(",")){
        list.add(ingredient)
    }
    return list
}

fun List<String>.convertIngredientsListToString(): String{
    val ingredients = StringBuilder()
    for(ingredient in this){
        ingredients.append("$ingredient,")
    }
    return ingredients.toString()
}