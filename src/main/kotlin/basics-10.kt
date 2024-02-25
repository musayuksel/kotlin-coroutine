import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

fun main() = runBlocking {
    println("Main program starts: ${Thread.currentThread().name}") // main thread

    withTimeout(1200) {
        try {
            doSomeDelayedJobs()
        } catch (ex: TimeoutCancellationException) { // It will throw TimeoutCancellationException
            println("TimeoutCancellationException: $ex")
        } finally {
            println("job: I'm running finally")
        }
    }

    // Program will crash before here!
    println("Main program ends: ${Thread.currentThread().name}")
}

private suspend fun doSomeDelayedJobs(): String {
    repeat(1000) { i ->
        println("job: I'm sleeping $i ...")
        delay(400L)
    }
    return "Hello world!!!"
}