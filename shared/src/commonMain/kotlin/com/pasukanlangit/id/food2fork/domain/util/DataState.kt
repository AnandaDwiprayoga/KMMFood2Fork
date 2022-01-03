package com.pasukanlangit.id.food2fork.domain.util

import com.pasukanlangit.id.food2fork.domain.model.GenericMessageInfo

data class DataState<T>(
    val message: GenericMessageInfo? = null,
    val data: T? = null,
    val isLoading: Boolean = false
){
    companion object {
        fun <T> error(
            message: GenericMessageInfo
        ): DataState<T>{
            return DataState(
                message = message
            )
        }

        fun <T> data(
            message: GenericMessageInfo? = null,
            data: T? = null
        ): DataState<T> {
            return DataState(
                message = message,
                data = data
            )
        }

        fun <T> loading(): DataState<T> =
            DataState(isLoading = true)
    }
}
