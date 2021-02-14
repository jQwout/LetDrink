package io.letdrink.common.utils

import kotlinx.coroutines.*
import org.junit.Test

import org.junit.Assert.*

class CoroutineTrampolineTest {

    @Test
    fun launchWithJob() = runBlocking {
        val t = CoroutineTrampoline(
            CoroutineScope(Dispatchers.Default),
            CoroutineScope(Dispatchers.IO),
        )

        var job = SupervisorJob()

        t.launchWithJob(job, onIO = {
            delay(100)
            "COMPLETE"
        }, onUI = {
            println(it)
        })

        job.cancelChildren()
        job = Job()

        t.launchWithJob(job, onIO = {
            "COMPLETE 2"
        }, onUI = {
            println(it)
        })

        delay(1000)

    }
}