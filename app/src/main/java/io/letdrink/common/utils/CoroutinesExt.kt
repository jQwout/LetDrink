package io.letdrink.common.utils

import kotlinx.coroutines.*

class CoroutineTrampoline(private val ui: CoroutineScope, private val io: CoroutineScope) {

    fun <P> launch(
        onIO: suspend () -> P,
        onUI: suspend (P) -> Unit,
        onError: ((Throwable) -> Unit)? = null
    ) {
        ui.launch {
            runCatching { withContext(io.coroutineContext) { onIO() } }
                .onSuccess { onUI(it) }
                .onFailure {
                    if(it !is CancellationException){
                        onError?.invoke(it)
                    }
                }
        }
    }

    fun <P> launchWithJob(
        superVisor: Job,
        onIO: suspend () -> P,
        onUI: suspend (P) -> Unit,
        onError: ((Throwable) -> Unit)? = null
    ) {
        SupervisorJob()
        val job = Job(superVisor)
        ui.launch(job) {
            runCatching { withContext(io.coroutineContext) { onIO() } }
                .onSuccess { onUI(it) }
                .onFailure {
                    if(it !is CancellationException){
                        onError?.invoke(it)
                    }
                }
        }
    }
}

fun CompletableJob.cancelChildrenAndCreateNew(): CompletableJob {
    cancel(CancellationException())
    return SupervisorJob()
}