import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking { // Creates a blocking coroutine scope on the main thread
    println("Main program starts: ${Thread.currentThread().name}") // main: execution within runBlocking

    GlobalScope.launch { // Launches a new coroutine in a global scope, potentially using a background thread
        doSomeDelayedJobs()
    }

    // Blocks the main thread within runBlocking, waiting for the launched coroutine to complete
    delay(1500)
    println("Main program ends: ${Thread.currentThread().name}") // Still main, as runBlocking blocks it
}

suspend fun doSomeDelayedJobs() {
    println("Coroutine starts: ${Thread.currentThread().name}") // Actual thread depends on dispatcher
    waitForTimes(1000) // Calls another suspend function
    println("Coroutine ends: ${Thread.currentThread().name}") // Same thread as above
}

suspend fun waitForTimes(time: Long) {
    // Other logic (if any)
    delay(time) // Suspends this coroutine without blocking the thread
}
