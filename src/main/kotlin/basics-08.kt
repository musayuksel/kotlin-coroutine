import kotlinx.coroutines.*

fun main() = runBlocking {
    println("Main program starts: ${Thread.currentThread().name}") // main thread

    val job = launch {
        try {
            for (i in 0..500) {
                println("Non delay job: I'm job $i ...")
                yield()// Without yield we can not cancel the job until it finish it
            }
        } catch (e: CancellationException) {
            println(e)
        } finally {
            println("job: I'm running finally")
        }
    }
    delay(2)
    println("main: I'm tired of waiting!")
    job.cancelAndJoin()
    println("main: Now I can quit.")
    println("Main program ends: ${Thread.currentThread().name}")
}
