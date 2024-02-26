import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("Main program starts: ${Thread.currentThread().name}")

    try {
        doSomeConcurrentJobWithError()
    } catch (ex: Exception) {
        println("Exception $ex")
    }

    println("Main program ends: ${Thread.currentThread().name}")

}

suspend fun doSomeConcurrentJobWithError() = coroutineScope {
    val msg1 = async {
        try {
            delay(Long.MAX_VALUE) // Emulates very long computation
            "Hello"
        } finally {
            println("First child was cancelled")
        }
    }
    val msg2 = async {
        println("Second child throws an exception")
        throw Exception("Some errors!!!!")
        "World!!!"
    }
    println("Return data: ${msg1.await() + msg2.await()}")
}

