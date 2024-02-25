import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

fun main() = runBlocking {
    println("Main program starts: ${Thread.currentThread().name}") // main thread

    val someData: String? = withTimeoutOrNull(1200) {//Returns data from fun or null
        doSomeDelayedJobs()
    }
    // Program will NOT crash before here!
    println("The return data is $someData")
    println("Main program ends: ${Thread.currentThread().name}")
}

private suspend fun doSomeDelayedJobs(): String {
    repeat(100) { i ->
        println("job: I'm sleeping $i ...")
        delay(400L)
    }
    return "Hello world!!!"
}