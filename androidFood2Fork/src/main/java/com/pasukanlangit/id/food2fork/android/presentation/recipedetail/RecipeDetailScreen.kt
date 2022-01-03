package com.pasukanlangit.id.food2fork.android.presentation.recipedetail

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.pasukanlangit.id.food2fork.android.presentation.components.LoadingRecipeShimmer
import com.pasukanlangit.id.food2fork.android.presentation.components.RECIPE_IMAGE_HEIGHT
import com.pasukanlangit.id.food2fork.android.presentation.recipedetail.components.RecipeView
import com.pasukanlangit.id.food2fork.android.presentation.theme.AppTheme
import com.pasukanlangit.id.food2fork.presentations.recipedetail.RecipeDetailEvents


@ExperimentalCoilApi
@ExperimentalStdlibApi
@Composable
fun RecipeDetailScreen(){
    val viewModel = hiltViewModel<RecipeDetailViewModel>()
    val recipeState = viewModel.recipe.value

    AppTheme(
        displayProgressBar = recipeState.isLoading,
        dialogQueue = recipeState.errorQueue,
        removeHeadQueue = {
            viewModel.onTriggerEvents(RecipeDetailEvents.removeHeadMessageQueue)
        }
    ) {
        val recipe = recipeState.recipe
        if(recipe == null && recipeState.isLoading){
            LoadingRecipeShimmer(imageHeight = RECIPE_IMAGE_HEIGHT, repeatCount = 1, repeatItemBarCount = 3)
        }
        else if(recipe == null){
            Text(
                modifier = Modifier.padding(16.dp),
                text = "We were unable to retrieve the details for this recipe.\nTry resetting the app.",
                style = MaterialTheme.typography.body1
            )
        }else{
            RecipeView(recipe = recipe)
        }
    }
}