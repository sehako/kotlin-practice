package part03.chapter15.section2

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import part03.chapter14.log
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

// 코루틴을 취소하면 해당 코루틴의 모든 자식 코루틴도 자동으로 취소된다.
fun cancelPropagationExample() {
    runBlocking {
        val job = launch {
            launch {
                launch {
                    log("I'm started")
                    delay(500.milliseconds)
                    log("I'm done")
                }
            }
        }

        delay(200.milliseconds)
        job.cancel()
    }

    /*
    0 [main @coroutine#4]: I'm started
     */
}

// 취소는 특별한 지점에서 CancellationException을 던지면 작동한다.
// 일반적으로 코루틴의 모든 일시 중단 함수는 CancellationException이 던져질 수 있는 지점을 도입한다.
// 그리고 코루틴은 예외를 사용해 코루틴 계층에서 취소를 전파한다.
// 따라서 이 예외를 직접 처리하지 않도록 주의해야 한다.
suspend fun doWork() {
    // CancellationException이 던져지는 부분
    delay(1000.milliseconds)
    throw UnsupportedOperationException("Error!")
}

fun coroutineTryCatchExample() {
    runBlocking {
        withTimeoutOrNull(2.seconds) {
            while (true) {
                try {
                    doWork()
                } catch (e: Exception) { // 모든 예외를 처리하도록 되어 있어서 CancellationException도 처리됨
                    println(e.message)
                }
            }
        }
    }

    /*
    Error!
    ...
    Timed out waiting for 2000 ms
     */
}
