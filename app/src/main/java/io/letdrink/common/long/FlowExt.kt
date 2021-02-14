package io.letdrink.common.long

import io.letdrink.common.state.ScreenState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNot

suspend inline fun Flow<out ScreenState>.collectNotEmpty(crossinline action: suspend (value: ScreenState) -> Unit) {
    filterNot { it is ScreenState.Empty }.collect(action)
}

suspend inline fun <reified T : ScreenState> Flow<ScreenState>.collectOnly(crossinline action: suspend (value: T) -> Unit) {
    filter { it is T }.collect {
        action(it as T)
    }
}