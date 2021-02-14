package io.letdrink.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.letdrink.common.utils.CoroutineTrampoline
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseViewModel<T> : ViewModel() {

    protected val backgroundScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    protected val trampoline = CoroutineTrampoline(viewModelScope, backgroundScope)

    abstract val uiState: MutableStateFlow<T>

    val lastState get() = uiState.value

    fun <P> async(
        onIO: suspend () -> P,
        onUI: suspend (P) -> Unit,
        onError: ((Throwable) -> Unit)? = null
    ) = trampoline.launch(onIO, onUI, onError)

    fun <P> asyncOnce(
        job: Job,
        onIO: suspend () -> P,
        onUI: suspend (P) -> Unit,
        onError: ((Throwable) -> Unit)? = null
    ) = trampoline.launchWithJob(job, onIO, onUI, onError)

    override fun onCleared() {
        super.onCleared()
        backgroundScope.coroutineContext.cancel()
    }
}