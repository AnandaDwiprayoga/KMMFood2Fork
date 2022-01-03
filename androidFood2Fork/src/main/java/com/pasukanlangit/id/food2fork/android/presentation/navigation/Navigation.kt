package com.pasukanlangit.id.food2fork.android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.pasukanlangit.id.food2fork.android.presentation.recipedetail.RecipeDetailScreen
import com.pasukanlangit.id.food2fork.android.presentation.recipelist.RecipeListScreen

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@ExperimentalStdlibApi
@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.RecipeList.route){
        composable(route = Screens.RecipeList.route){
            RecipeListScreen(
                onSelectedRecipeId = { recipeId ->
                    navController.navigate("${Screens.RecipeDetail.route}/$recipeId")
                }
            )
        }

        composable(
            route = "${Screens.RecipeDetail.route}/{recipeId}",
            arguments = listOf(
                navArgument(
                    name = "recipeId"
                ){
                    type = NavType.IntType
                }
            )
        ){
            RecipeDetailScreen()
        }
    }
}