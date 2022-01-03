package com.pasukanlangit.id.food2fork.interactors.recipeList

import com.pasukanlangit.id.food2fork.datasource.cache.RecipeCache
import com.pasukanlangit.id.food2fork.datasource.network.RecipeService
import com.pasukanlangit.id.food2fork.domain.model.GenericMessageInfo
import com.pasukanlangit.id.food2fork.domain.model.Recipe
import com.pasukanlangit.id.food2fork.domain.model.UIComponentType
import com.pasukanlangit.id.food2fork.domain.util.CommonFlow
import com.pasukanlangit.id.food2fork.domain.util.DataState
import com.pasukanlangit.id.food2fork.domain.util.asCommonFlow
import kotlinx.coroutines.flow.flow

class SearchRecipes(
    private val recipeService: RecipeService,
    private val recipeCache: RecipeCache
) {
    operator fun invoke(
        page: Int,
        query: String
    ): CommonFlow<DataState<List<Recipe>>> = flow {
        emit(DataState.loading())
        try {
            val recipes = recipeService.search(page, query)
            recipeCache.insert(recipes)

            if(query == "error"){
                throw Exception("This test error, should show popup")
            }

            val cachedResult = if(query.isBlank()){
                recipeCache.getAll(page)
            }else{
                recipeCache.search(query, page)
            }

            emit(DataState.data(message = null, data = cachedResult))
        }catch (e: Exception){
            emit(DataState.error(
                message = GenericMessageInfo.Builder()
                    .id("${this@SearchRecipes::class.simpleName}.Error")
                    .title("Error")
                    .uiComponentType(UIComponentType.Dialog)
                    .description(e.message ?: "Unknown Error")
                    .build()
            ))
        }
    }.asCommonFlow()
}