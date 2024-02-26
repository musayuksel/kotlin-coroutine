import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    println("Main program starts: ${Thread.currentThread().name}") // main thread

    val totalTime = measureTimeMillis {
        val msg1: Deferred<String> = async { doSomeDelayedJobsOne() }
        val msg2: Deferred<String> = async {
            // can implement other logic
            doSomeDelayedJobsTwo()
        } // will run concurrent manner
        println("Return data: ${msg1.await() + msg2.await()}")
    }
    println("Total time is: $totalTime")

    println("Main program ends: ${Thread.currentThread().name}")
}

private suspend fun doSomeDelayedJobsOne(): String {
    delay(1000)
    return "Hello "
}

private suspend fun doSomeDelayedJobsTwo(): String {
    delay(1000)
    return "world!!!"
}