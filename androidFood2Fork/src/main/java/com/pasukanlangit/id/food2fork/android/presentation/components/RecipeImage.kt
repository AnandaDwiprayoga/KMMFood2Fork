package com.pasukanlangit.id.food2fork.android.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter

val RECIPE_IMAGE_HEIGHT: Dp = 260.dp

@ExperimentalCoilApi
@Composable
fun RecipeImage(
    url: String,
    contentDescription: String
){
    val painter = rememberImagePainter(url)
    Box {
        Image(
            painter = painter,
            modifier = Modifier
                .fillMaxWidth()
                .height(RECIPE_IMAGE_HEIGHT),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop
        )
        when(painter.state){
            is ImagePainter.State.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(RECIPE_IMAGE_HEIGHT)
                ){
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
            is ImagePainter.State.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(RECIPE_IMAGE_HEIGHT)
                ){
                    Text(
                        "Image Error",
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            else -> {}
        }
    }
}