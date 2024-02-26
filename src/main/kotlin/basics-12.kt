import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    println("Main program starts: ${Thread.currentThread().name}") // main thread

    val totalTime = measureTimeMillis {
        val msg1 = doSomeDelayedJobsOne()
        val msg2 = doSomeDelayedJobsTwo() // will run sequential
        println("Return data: ${msg1 + msg2}")
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