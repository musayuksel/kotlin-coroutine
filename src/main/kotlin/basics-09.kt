import kotlinx.coroutines.*

fun main() = runBlocking {
    println("Main program starts: ${Thread.currentThread().name}") // main thread

    val job = launch(Dispatchers.Default) {//we will cover Dispatchers soon
        var someJob = 0;
        try {
            while (isActive) {
                //we don't need to use delay/yield
                println("Non delay job: I'm job ${someJob++}...")
            }
        } catch (e: CancellationException) {
            //doesn't throw ex
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
