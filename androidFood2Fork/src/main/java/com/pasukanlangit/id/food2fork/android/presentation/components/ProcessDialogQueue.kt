package com.pasukanlangit.id.food2fork.android.presentation.components

import androidx.compose.runtime.Composable
import com.pasukanlangit.id.food2fork.domain.model.GenericMessageInfo
import com.pasukanlangit.id.food2fork.domain.util.Queue

@Composable
fun ProcessDialogQueue(
   dialogQueue: Queue<GenericMessageInfo>?,
   removeHeadQueue: () -> Unit
) {
    dialogQueue?.peek()?.let { message ->
        GenericDialog(
            genericMessageInfo = message,
            removeHeadQueue = removeHeadQueue
        )
    }
}