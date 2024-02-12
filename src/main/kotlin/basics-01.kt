import kotlin.concurrent.thread

fun main() { // this: CoroutineScope
    println("Main program starts: ${Thread.currentThread().name}")

    thread {// background thread
        println("Background thread starts: ${Thread.currentThread().name}")
        Thread.sleep(1000) //some async jobs
        println("Background thread ends: ${Thread.currentThread().name}")
    }

    println("Main program ends: ${Thread.currentThread().name}")
}