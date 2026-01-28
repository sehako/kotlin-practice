package part03.chapter15.section2

import kotlinx.coroutines.*
import part03.chapter14.log
import kotlin.time.Duration.Companion.milliseconds

// 코드를 취소 가능하게 만드는 유틸리티 함수는 ensureActive와 yield가 있으며,
// 속성으로는 isActive가 존재한다.

fun coroutineCancellationExample() {
    runBlocking {
        val myJob = launch {
            repeat(5) {
                doCpuHardWork()
                // false면 현재 코루틴 스코프가 취소된 것
//                if (!isActive) return@launch

                // if를 직접적으로 명시하는 대신 코루틴이 활성 상태가 아닌 경우
                // CancellationException을 던지는 ensureActive를 사용할 수도 있다.
                ensureActive()
            }
        }
        delay(600.milliseconds)
        myJob.cancel()
    }
}

// yield는 코드에서 쥐소 가능 지점을 제공하는 동시에 현재 점유된 디스패처에서 다른 코루틴이 작업할 수 있게 해준다.
suspend fun doCpuHardWorkYield(): Int {
    log("I'm doing work")
    var counter = 0
    val startTime = System.currentTimeMillis()
    while (System.currentTimeMillis() < startTime + 500) {
        counter++
        yield()
    }
    return counter
}

fun yieldExample() {
    runBlocking {
        launch {
            repeat(3) {
//                doCpuHardWork()
                doCpuHardWorkYield()
            }
        }

        launch {
            repeat(3) {
                // 일시 중단 지점이 없는 함수이므로 해당 코루틴은 첫 번째 launch가 종료될때까지 실행될 수 없다.
                // isActive나 ensureActive는 취소 여부를 확인할 뿐 실제로 코루틴을 일시 중단시키지는 않으므로
                // 이를 사용해도 개선할 수 없다.
//                doCpuHardWork()

                doCpuHardWorkYield()
            }
        }
    }

    /*
    0 [main @coroutine#2]: I'm doing work
    6 [main @coroutine#3]: I'm doing work
    505 [main @coroutine#2]: I'm doing work
    506 [main @coroutine#3]: I'm doing work
    1005 [main @coroutine#2]: I'm doing work
    1006 [main @coroutine#3]: I'm doing work
     */
}

// yield는 대기 중인 다른 코루틴이 있으면 디스패처가 제어를 다른 코루틴에게 넘길 수 있게 해준다.
// 이들을 정리하면 다음과 같다.
/*
isActive: 취소 요청 확인
ensureActive: 취소 지점 도입하여 취소 시 CancellationException을 던져 즉시 작업 중단.
yield: CPU Bound 작업에서 기저 스레드나 스레드 풀 소모 방지를 위한 계산 자원 양도
 */
