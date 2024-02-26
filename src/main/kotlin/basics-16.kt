import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("Main program starts: ${Thread.currentThread().name}")

    println("runBlocking Scope: $this")
    launch {
        println("Parent launch Scope: $this")

        async {
            println("async Scope: $this")
        }
        launch {
            println("Child launch Scope: $this")
        }
    }
    println("Main program ends: ${Thread.currentThread().name}")

}



