import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    println("Main program starts: ${Thread.currentThread().name}") // main thread
    val totalTime = measureTimeMillis {
        val msg1: Deferred<String> = async(start = CoroutineStart.LAZY) { doSomeDelayedJobsOne() }
        val msg2: Deferred<String> = async(start = CoroutineStart.LAZY) { doSomeDelayedJobsTwo() }
        val msg3: Deferred<String> = async(start = CoroutineStart.LAZY) { doSomeUnnecessaryDelayedJobs() }
        //some other logic
        msg1.start()
        msg2.start()
        println("Return data: ${msg1.await() + msg2.await()}")
    }
    println("Total time is: $totalTime")
    println("Main program ends: ${Thread.currentThread().name}")
}

private suspend fun doSomeDelayedJobsOne(): String {
    delay(1000)
    println("doSomeDelayedJobsOne run")
    return "Hello "
}

private suspend fun doSomeDelayedJobsTwo(): String {
    delay(1000)
    println("doSomeDelayedJobsTwo run")
    return "world!!!"
}

private suspend fun doSomeUnnecessaryDelayedJobs(): String {
    delay(1000)
    println("doSomeUnnecessaryDelayedJobs-> run")
    return "You don't need this!"
}