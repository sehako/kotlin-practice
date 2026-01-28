package part03.chapter15.section2

import kotlinx.coroutines.*
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

// 제한 시간이 지나면 코루틴을 취소하는 기능이다.
suspend fun calculateSomething(): Int {
    delay(3.seconds)
    return 2 + 2
}

fun timeoutFunctionExample() = runBlocking {
    // 시간 초과가 발생하면 null을 반환
    val resultTimeOut = withTimeoutOrNull(500.milliseconds) { calculateSomething() }

    println(resultTimeOut)
    try {
        // 시간 초과가 발생하면 TimeoutCancellationException을 반환
        val nonNullableResult = withTimeout(500.milliseconds) { calculateSomething() }
    } catch (e: TimeoutCancellationException) {
        println("시간 초과")
    }

}
