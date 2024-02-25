import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.CancellationException

fun main() = runBlocking {
    println("Main program starts: ${Thread.currentThread().name}") // main thread

    val myCancellableJob = launch {
        try {
            doSomeDelayedJobs()
        } catch (e: CancellationException) {
            println(e)
        } finally {
            println("job: I'm running finally")
        }
    }
    waitForTimes(1300L)
    println("main: I'm tired of waiting!")
    myCancellableJob.cancelAndJoin() // myCancellableJob will throw an `CancellationException` which handled in the catch block
    println("main: Now I can quit.")

    println("Main program ends: ${Thread.currentThread().name}")
}

private suspend fun doSomeDelayedJobs(): String {
    repeat(1000) { i ->
        println("job: I'm sleeping $i ...")
        waitForTimes(500L)
    }
    return "Hello world!!!"
}

private suspend fun waitForTimes(time: Long) {
    // Other logic (if any)
    delay(time) // Suspends this coroutine without blocking the thread
}
