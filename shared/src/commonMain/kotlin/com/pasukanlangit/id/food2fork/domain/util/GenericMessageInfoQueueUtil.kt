package com.pasukanlangit.id.food2fork.domain.util

import com.pasukanlangit.id.food2fork.domain.model.GenericMessageInfo

class GenericMessageInfoQueueUtil {
    fun doesMessageAlreadyExistInQueue(
        queue: Queue<GenericMessageInfo>,
        messageInfo: GenericMessageInfo
    ): Boolean {
        for(item in queue.items){
            if(item.id == messageInfo.id){
                return true
            }
        }
        return false
    }
}