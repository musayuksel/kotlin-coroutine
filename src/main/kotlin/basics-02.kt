import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun main() { // this: CoroutineScope
    println("Main program starts: ${Thread.currentThread().name}")

    GlobalScope.launch {// background coroutine runs on a background thread
        println("Coroutine starts in background: ${Thread.currentThread().name}")
        Thread.sleep(1000) //some async jobs
        println("Coroutine ends in background: ${Thread.currentThread().name}")
    }

    println("Main program ends: ${Thread.currentThread().name}")
}