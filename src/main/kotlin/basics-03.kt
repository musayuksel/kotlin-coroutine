import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() { // this: CoroutineScope
    println("Main program starts: ${Thread.currentThread().name}")

    GlobalScope.launch {//i.e T1
        println("Coroutine starts in background: ${Thread.currentThread().name}")

        delay(1000) // T1 is free - NOT blocked
        println("Coroutine ends in background: ${Thread.currentThread().name}")
    }

    //Blocks the current main thread - NOT a right way atm
    Thread.sleep(1500)
    println("Main program ends: ${Thread.currentThread().name}")
}