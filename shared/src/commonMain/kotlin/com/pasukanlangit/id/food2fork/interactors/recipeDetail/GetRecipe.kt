package com.pasukanlangit.id.food2fork.interactors.recipeDetail

import com.pasukanlangit.id.food2fork.datasource.cache.RecipeCache
import com.pasukanlangit.id.food2fork.domain.model.GenericMessageInfo
import com.pasukanlangit.id.food2fork.domain.model.Recipe
import com.pasukanlangit.id.food2fork.domain.model.UIComponentType
import com.pasukanlangit.id.food2fork.domain.util.CommonFlow
import com.pasukanlangit.id.food2fork.domain.util.DataState
import com.pasukanlangit.id.food2fork.domain.util.asCommonFlow
import kotlinx.coroutines.flow.flow


class GetRecipe(
    private val recipeCache: RecipeCache
) {
    operator fun invoke(
        idRecipe: Int
    ): CommonFlow<DataState<Recipe>> = flow {
        emit(DataState.loading())

        try {
            kotlinx.coroutines.delay(1000L)
            val recipe = recipeCache.get(idRecipe)
            emit(DataState.data(data = recipe))
        }catch (e: Exception){
            emit(DataState.error(
                message = GenericMessageInfo.Builder()
                    .id("${this@GetRecipe::class.simpleName}.Error")
                    .title("Error")
                    .uiComponentType(UIComponentType.Dialog)
                    .description(e.message ?: "Unknown Error")
                    .build()
            ))
        }
    }.asCommonFlow()
}