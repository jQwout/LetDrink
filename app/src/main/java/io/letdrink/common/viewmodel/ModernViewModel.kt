package io.letdrink.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class ModernViewModel : ViewModel() {

    val navigate: MutableStateFlow<VmEvents?> = MutableStateFlow(null)
    val dialog: MutableStateFlow<VmEvents?> = MutableStateFlow(null)

    protected fun emitLoading() = viewModelScope.launch {
        dialog.emit(VmEvents.Loading(true))
    }

    protected fun dismissLoading() = viewModelScope.launch {
        dialog.emit(VmEvents.Loading(false))
    }

    protected fun emitError(e: Throwable) = viewModelScope.launch {
        dialog.emit(VmEvents.Fail(e))
    }

    protected fun tryOnIOWithLoading(block: suspend () -> Unit) {
        emitLoading()
        tryOnIO(block = {
            block()
            dismissLoading()
        }, { emitError(it) }
        )
    }

    protected fun tryOnIOWithLoading(
        block: suspend () -> Unit,
        fallback: suspend (Throwable) -> Unit
    ) {
        emitLoading()
        tryOnIO(block = {
            block()
            dismissLoading()
        }, { fallback(it) }
        )
    }
}

fun ViewModel.launchOnIO(block: suspend () -> Unit) {
    viewModelScope.launch(Dispatchers.IO) {
        block()
    }
}

fun ViewModel.tryOnIO(block: suspend () -> Unit, onError: suspend (Throwable) -> Unit) {
    viewModelScope.launch(Dispatchers.IO) {
        try {
            block()
        } catch (e: Throwable) {
            onError(e)
        }
    }
}
