package com.pasukanlangit.id.food2fork.datasource.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeItemDto(

    @SerialName("date_updated")
    val dateUpdated: String,

    @SerialName("rating")
    val rating: Int,

    @SerialName("description")
    val description: String,

    @SerialName("title")
    val title: String,

    @SerialName("long_date_added")
    val longDateAdded: Int,

    @SerialName("featured_image")
    val featuredImage: String,

    @SerialName("source_url")
    val sourceUrl: String,

    @SerialName("date_added")
    val dateAdded: String,

    @SerialName("cooking_instructions")
    val cookingInstructions: String?,

    @SerialName("long_date_updated")
    val longDateUpdated: Int,

    @SerialName("publisher")
    val publisher: String,

    @SerialName("ingredients")
    val ingredients: List<String>,

    @SerialName("pk")
    val pk: Int
)
