import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking { // Creates a blocking coroutine scope on the main thread
    println("Main program starts: ${Thread.currentThread().name}") // main thread

    val someDelayedJob: Job = launch { // main -inherit from immediate parent
        doSomeDelayedJobs()
    }

    someDelayedJob.join()//Suspends the coroutine until this job is complete!!!

    println("Main program ends: ${Thread.currentThread().name}") // Still main, as runBlocking blocks it
}

private suspend fun doSomeDelayedJobs() {
    println("Coroutine starts: ${Thread.currentThread().name}")// main
    waitForTimes(1000) // Calls another suspend function, main thread free(not blocked)
    println("Coroutine ends: ${Thread.currentThread().name}") // either main or some other thread
}

private suspend fun waitForTimes(time: Long) {
    // Other logic (if any)
    delay(time) // Suspends this coroutine without blocking the thread
}
