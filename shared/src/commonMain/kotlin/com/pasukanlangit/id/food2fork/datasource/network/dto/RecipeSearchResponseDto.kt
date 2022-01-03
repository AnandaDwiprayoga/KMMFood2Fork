package com.pasukanlangit.id.food2fork.datasource.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeSearchResponseDto(

	@SerialName("next")
	val next: String,

	@SerialName("previous")
	val previous: String?,

	@SerialName("count")
	val count: Int,

	@SerialName("results")
	val results: List<RecipeItemDto>
)
