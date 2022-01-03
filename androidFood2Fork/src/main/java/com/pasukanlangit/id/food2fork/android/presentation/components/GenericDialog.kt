package com.pasukanlangit.id.food2fork.android.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pasukanlangit.id.food2fork.domain.model.GenericMessageInfo

@Composable
fun GenericDialog(
    genericMessageInfo: GenericMessageInfo,
    removeHeadQueue: () -> Unit
){
    AlertDialog(
        onDismissRequest = {
            removeHeadQueue()
            genericMessageInfo.onDismiss?.invoke()
        },
        text = {
            val description = remember { genericMessageInfo.description }
            if(description != null){
                Text(
                    text = description,
                    style = MaterialTheme.typography.body1
                )
            }
        },
        title = {
            Text(
                text = genericMessageInfo.title,
                style = MaterialTheme.typography.h3
            )
        },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                val negativeButton = remember { genericMessageInfo.negativeAction }
                val positiveAction = remember { genericMessageInfo.positiveAction }

                if(negativeButton != null){
                    Button(
                        modifier = Modifier.padding(end = 8.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onError),
                        onClick = {
                            removeHeadQueue()
                            negativeButton.onBtnClicked()
                        }
                    ){
                        Text(text = negativeButton.btnLabel)
                    }
                }

                if(positiveAction != null){
                    Button(
                        modifier = Modifier.padding(end = 8.dp),
                        onClick = {
                            removeHeadQueue()
                            positiveAction.onBtnClicked()
                        }
                    ){
                        Text(text = positiveAction.btnLabel)
                    }
                }

            }
        }
    )
}