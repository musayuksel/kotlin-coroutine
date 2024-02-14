import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

private suspend fun mySuspendFun():String {
    // Other logic
    delay(5000)
    return "Hello world!"
}
internal class SampleTest {
    @Test
    fun testSum() = runBlocking {
        val expected = "Hello world!"
        val actual = mySuspendFun()

        assertEquals(expected, actual)
    }
}