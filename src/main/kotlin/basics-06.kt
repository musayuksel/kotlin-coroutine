import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking { // Creates a blocking coroutine scope on the main thread
    println("Main program starts: ${Thread.currentThread().name}") // main thread

    val someDelayedDeferred: Deferred<String> = async {
        doSomeDelayedJobs()//will return some string data
    }
    // Main program continues without blocking

    val someData = someDelayedDeferred.await() // Suspends until result is ready

    println("Data returned from server: $someData")

    println("Main program ends: ${Thread.currentThread().name}") // Still main, as runBlocking blocks it
}

private suspend fun doSomeDelayedJobs(): String {
    println("Coroutine starts: ${Thread.currentThread().name}")// main
    waitForTimes(1000) // Calls another suspend function, main thread free(not blocked)
    println("Coroutine ends: ${Thread.currentThread().name}") // either main or some other thread

    return "Hello world!!!"
}

private suspend fun waitForTimes(time: Long) {
    // Other logic (if any)
    delay(time) // Suspends this coroutine without blocking the thread
}
