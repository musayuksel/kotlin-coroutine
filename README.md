# Kotlin Coroutines:

Kotlin coroutines provide a powerful and lightweight alternative to traditional threads for managing asynchronous code
in your application. They offer a structured approach to concurrency while avoiding the complexity and overhead of
thread management.

However, running coroutines on the main thread requires careful consideration as it can lead to performance issues and
UI freezes if not handled correctly.

## Understanding the Main Thread

The main thread is the primary thread responsible for handling UI interactions and updates in your application. **Any
work done on the main thread should be lightweight and avoid blocking operations** that prevent the thread from
processing other critical tasks. This includes:

* **UI interactions:** Button clicks, scroll events, text input
* **Small math operations:** Simple calculations that complete quickly

**Why Avoid Blocking Operations?**

Heavy operations like file downloads, image loading, and complex network requests can block the main thread, causing your application to freeze and become unresponsive. This leads to a poor user experience and potential ANRs (Application Not Responding) errors.

### Background Threads
While creating dedicated background threads for each operation seems like a logical solution, it's not ideal. Here's why:

**Resource Intensive:** Each thread consumes memory and system resources, leading to potential performance bottlenecks and crashes, especially when creating many threads.

**Context Switching Overhead:** Frequent switching between threads incurs overhead, further impacting performance and efficiency.

**Complexity Management:** Manually managing numerous threads becomes complex and error-prone, increasing the difficulty of maintenance and debugging.

**This is where Kotlin coroutines come in as lifesavers!!!**

With Kotlin coroutines, we can utilize a single background thread and launch multiple coroutines on it. Coroutines are lightweight and highly efficient, allowing us to achieve concurrent and asynchronous operations without the overhead of managing multiple threads.

<div align="center">
    <img src="./assets/coroutine-1.png" alt="Kotlin Coroutines" width="300">
</div>

**Be careful Coroutine != Thread**

**For threads:**

The main program starts a background thread and then continues to execute and exit independently. The background thread will run concurrently with the main thread and eventually exit on its own.:
```kotlin
fun main() { // this: CoroutineScope
    println("Main program starts: ${Thread.currentThread().name}")

    thread {// background thread
        println("Background thread starts: ${Thread.currentThread().name}")
        Thread.sleep(1000) //some async jobs
        println("Background thread ends: ${Thread.currentThread().name}")
    }

    println("Main program ends: ${Thread.currentThread().name}")
}
/* output
Main program starts: main
Main program ends: main
Mock job starts: Thread-0
Mock job ends: Thread-0

Process finished with exit code 0 
 The main thread starts and ends immediately, while the background thread performs a mock job asynchronously and then ends. 
 */
```

**For coroutines:**
- While the main thread finishes quickly, the coroutine runs independently on a background thread.
- The main program doesn't wait for the coroutine to finish, allowing the UI to remain responsive.
- This is asynchronous nature of coroutines - they don't block the main thread.
```kotlin
fun main() { // this: CoroutineScope
    println("Main program starts: ${Thread.currentThread().name}")

    GlobalScope.launch {// background coroutine runs on a background thread
        println("Coroutine starts in background: ${Thread.currentThread().name}")
        Thread.sleep(1000) //some async jobs
        println("Coroutine ends in background: ${Thread.currentThread().name}")
    }

    println("Main program ends: ${Thread.currentThread().name}")
}

/*output

Main program starts: main
Main program ends: main

Process finished with exit code 0
 */
```

**How can we ensure that the coroutines complete their tasks before we proceed?**

What if I use `delay` instead of `Thread.sleep(1000)`?

```kotlin
fun main() { // this: CoroutineScope
    println("Main program starts: ${Thread.currentThread().name}")

    GlobalScope.launch {//i.e T1
        println("Coroutine starts in background: ${Thread.currentThread().name}") 

        delay(1000) // T1 is free - NOT blocked
        println("Coroutine ends in background: ${Thread.currentThread().name}") //T1
    }

    //Blocks the current main thread - NOT a right way atm
    Thread.sleep(1500)
    println("Main program ends: ${Thread.currentThread().name}")//T1 or some other thread
}
/* 
output:
Main program starts: main
Coroutine starts in background: DefaultDispatcher-worker-1
Coroutine ends in background: DefaultDispatcher-worker-1//might be different
Main program ends: main

Process finished with exit code 0
 */
```

`Thread.sleep` pauses the **entire thread**, which isn't ideal for coroutines.

By replacing it with `delay(1000)`, we achieve a more efficient and coroutine-friendly approach:

- **Non-blocking:** delay suspends the coroutine only, allowing other coroutines to run concurrently. The main thread remains responsive.
- **Lightweight:** Compared to thread creation, coroutines and delay are more resource-efficient.
- **Flexibility:** delay can be cancelled or resumed, offering more control over asynchronous behavior.

While the overall output might seem similar the underlying behavior differs. With `delay`, the main program and the coroutine execute concurrently, enhancing performance and UI responsiveness.

## Suspend fun
Kotlin coroutines rely on a special function modifier called `suspend` to define coroutine functions which  are the building blocks of asynchronous operations within your coroutines.

**Key characteristics of `suspend fun`:**
- **Can't call directly from the main thread:** They require a coroutine context to be launched within.
- **Can call other suspend fun:** This enables building complex asynchronous workflows by chaining multiple suspendable functions.
- **Use await for results:** When waiting for the result of another suspend fun, use await to retrieve it after it finishes.

<details>
<summary><i>Example</i></summary>

```kotlin
fun main() = runBlocking { // Creates a blocking coroutine scope on the main thread
    println("Main program starts: ${Thread.currentThread().name}") // main: execution within runBlocking

    GlobalScope.launch { // Launches a new coroutine in a global scope, potentially using a background thread
        doSomeDelayedJobs()
    }

    // Blocks the main thread within runBlocking, waiting for the launched coroutine to complete
    delay(1500)
    println("Main program ends: ${Thread.currentThread().name}") // Still main, as runBlocking blocks it
}

suspend fun doSomeDelayedJobs() {
    println("Coroutine starts: ${Thread.currentThread().name}") // Actual thread depends on dispatcher
    waitForTimes(1000) // Calls another suspend function
    println("Coroutine ends: ${Thread.currentThread().name}") // Same thread as above
}

suspend fun waitForTimes(time: Long) {
    // Other logic (if any)
    delay(time) // Suspends this coroutine without blocking the thread
}
```
</details>

## Coroutines Builders
### What are Coroutine Builders?

Think of them as specialized functions or keywords that streamline coroutine creation and control. Each builder offers unique ways to launch and orchestrate your coroutines based on your specific needs and threading configurations.

### Common Builders:
<details>
<summary><b>launch</b></summary>
Launches a <i>fire-and-forget</i> coroutine, perfect for background tasks that don't need to return a value. Imagine it as starting a worker drone, sending them off on their mission without expecting a report.

</details>

<details>
<summary><b>async</b></summary>
It launches a coroutine that produces a valuable result later. Once the drone delivers, you use await to retrieve the precious cargo.
</details>

<details>
<summary><b>runBlocking</b></summary>
This builder acts like a temporary roadblock. It creates a blocking coroutine scope, executing all launched coroutines sequentially and keeping the current thread waiting until they finish. However, <b>tread carefully in production code</b>, as it can freeze your UI!
</details>

<details>
<summary><b>coroutineScope</b></summary>
Similar to runBlocking, but without the roadblock. This builder creates a non-blocking scope, letting your coroutines run freely while keeping an eye on them. Use it to launch multiple tasks and patiently wait for all to complete before moving on.
</details>

<details>
<summary><b>withContext</b></summary>
Need to send your coroutine on a specific mission? This builder is your travel agent. It allows you to switch the current coroutine's context, choosing a different dispatcher like a background thread or even the main thread.
</details>

### Why are Builders Important?

They're guardians of clean and efficient code. They promote structure, avoid the complexities of manual thread management, and unlock the true potential of asynchronous programming.
