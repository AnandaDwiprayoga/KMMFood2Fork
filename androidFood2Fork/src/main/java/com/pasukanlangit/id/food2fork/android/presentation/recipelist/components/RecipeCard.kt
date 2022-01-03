package com.pasukanlangit.id.food2fork.android.presentation.recipelist.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.pasukanlangit.id.food2fork.android.presentation.components.RecipeImage
import com.pasukanlangit.id.food2fork.domain.model.Recipe


@ExperimentalCoilApi
@Composable
fun RecipeCard(
    recipe: Recipe,
    onClick: () -> Unit
){
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp, top = 6.dp,)
            .clickable { onClick() },
        elevation = 8.dp
    ) {
        Column {
            RecipeImage(url = recipe.featuredImage, contentDescription = recipe.title)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 8.dp)
            ) {
                Text(
                    text = recipe.title,
                    modifier = Modifier
                        .fillMaxWidth(.85f)
                        .wrapContentWidth(Alignment.Start),
                    style = MaterialTheme.typography.h3
                )
                val rank = recipe.rating.toString()
                Text(
                    text = rank,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.End)
                        .align(Alignment.CenterVertically),
                    style = MaterialTheme.typography.h5
                )
            }
        }
    }
}