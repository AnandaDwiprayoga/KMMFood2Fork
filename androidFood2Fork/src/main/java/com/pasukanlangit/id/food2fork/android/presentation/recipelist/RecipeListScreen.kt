package com.pasukanlangit.id.food2fork.android.presentation.recipelist

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.pasukanlangit.id.food2fork.android.presentation.recipelist.components.RecipeList
import com.pasukanlangit.id.food2fork.android.presentation.recipelist.components.SearchAppBar
import com.pasukanlangit.id.food2fork.android.presentation.theme.AppTheme
import com.pasukanlangit.id.food2fork.presentations.recipelist.RecipeListEvent

@ExperimentalComposeUiApi
@ExperimentalCoilApi
@Composable
fun RecipeListScreen(
    onSelectedRecipeId: (Int) -> Unit,
){
    val viewModel: RecipeListViewModel = hiltViewModel()
    val state = viewModel.state.value

    AppTheme(
        displayProgressBar = state.isLoading,
        dialogQueue = state.errorQueue,
        removeHeadQueue = {
            viewModel.onTriggerEvent(RecipeListEvent.removeHeadMessageQueue)
        }
    ) {
        Scaffold(
            topBar = {
                SearchAppBar(
                    query = state.query,
                    selectedCategory = state.selectedCategory,
                    onSelectedCategory = {
                        viewModel.onTriggerEvent(RecipeListEvent.OnSelectCategory(it))
                    } ,
                    onQueryChange = {
                        viewModel.onTriggerEvent(RecipeListEvent.OnQueryChange(it))
                    },
                    onExecuteSearch = {
                        viewModel.onTriggerEvent(RecipeListEvent.SubmitSearch)
                    }
                )
            }
        ) {
            RecipeList(
                recipes = state.recipes,
                isLoading = state.isLoading,
                page = state.page,
                onTriggerLoadNextRecipes = {
                    viewModel.onTriggerEvent(RecipeListEvent.LoadRecipes)
                },
                onItemClick = onSelectedRecipeId
            )
        }
    }
}