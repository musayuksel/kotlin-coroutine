import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking { // Creates a blocking coroutine scope on the main thread
    println("Main program starts: ${Thread.currentThread().name}") // main thread

   launch { // main ????
        doSomeDelayedJobs()
    }

    // Blocks the main thread within runBlocking, waiting for the launched coroutine to complete
    delay(1500)
    println("Main program ends: ${Thread.currentThread().name}") // Still main, as runBlocking blocks it
}

private  suspend fun doSomeDelayedJobs() {
    println("Coroutine starts: ${Thread.currentThread().name}")
    waitForTimes(1000) // Calls another suspend function
    println("Coroutine ends: ${Thread.currentThread().name}")
}

private suspend fun waitForTimes(time: Long) {
    // Other logic (if any)
    delay(time) // Suspends this coroutine without blocking the thread
}
