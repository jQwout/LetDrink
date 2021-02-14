package io.letdrink.common

import kotlinx.coroutines.flow.Flow

abstract class DataFlowRepository<T> {

    abstract fun get(response: Response? = null): Flow<DataFlowEvent<out T>>

    abstract fun put(data: T)
}

sealed class DataFlowEvent<T> {
    class Loading(val source: DataFlowSource) : DataFlowEvent<Nothing>()
    class Content<T>(val source: DataFlowSource, val value: T) : DataFlowEvent<T>()
    class Error(val source: DataFlowSource, val error: Throwable) : DataFlowEvent<Nothing>()
    class NoContent(val source: DataFlowSource) : DataFlowEvent<Nothing>()
}


enum class DataFlowSource {
    REMOTE, LOCAL
}

open class Response(val source: DataFlowSource)