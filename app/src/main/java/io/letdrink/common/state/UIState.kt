package io.letdrink.common.state


open class ScreenState {
    object Empty : ScreenState()
    class Error(val error: Throwable) : ScreenState()
}


data class SectionState<T>(
    val content: T? = null,
    val isLoading: Boolean = false
)
