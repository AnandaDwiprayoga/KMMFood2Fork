package com.pasukanlangit.id.food2fork.android.presentation.recipelist.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.pasukanlangit.id.food2fork.android.presentation.components.LoadingRecipeShimmer
import com.pasukanlangit.id.food2fork.android.presentation.components.RECIPE_IMAGE_HEIGHT
import com.pasukanlangit.id.food2fork.datasource.cache.RecipeCacheImpl.Companion.RECIPE_PAGINATION_PAGE_SIZE
import com.pasukanlangit.id.food2fork.domain.model.Recipe

@ExperimentalCoilApi
@Composable
fun RecipeList(
    recipes: List<Recipe>,
    isLoading: Boolean,
    page: Int,
    onTriggerLoadNextRecipes: () -> Unit,
    onItemClick: (Int) -> Unit
){
    if(isLoading && recipes.isEmpty()){
        //Loading
        LoadingRecipeShimmer(imageHeight = RECIPE_IMAGE_HEIGHT, repeatCount = 5)
    }
    else if(recipes.isEmpty()){

    }
    else{
        LazyColumn {
            itemsIndexed(items = recipes) { index, recipe ->
                if((index + 1) >= (page * RECIPE_PAGINATION_PAGE_SIZE) && !isLoading){
                    onTriggerLoadNextRecipes()
                }
                RecipeCard(
                    recipe = recipe
                ) {
                    onItemClick(recipe.id)
                }
                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }
}