package part03.chapter15.section2

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import part03.chapter14.log
import kotlin.time.Duration.Companion.milliseconds

// 코틀린 코루틴에 기본적으로 포함된 함수는 이미 취소 가능하다.
// 하지만 다음 코드를 보도록 하자.
suspend fun doCpuHardWork(): Int {
    log("I'm doing work")
    var counter = 0
    val startTime = System.currentTimeMillis()
    while (System.currentTimeMillis() - startTime < 3000) {
        counter++
    }
    return counter
}

fun cancelFailExample() {
    runBlocking {
        val myJob = launch {
            repeat(5) {
                doCpuHardWork()
            }
        }

        delay(600.milliseconds)
        myJob.cancel()
    }
    /*
    0 [main @coroutine#2]: I'm doing work
    3005 [main @coroutine#2]: I'm doing work
    6005 [main @coroutine#2]: I'm doing work
    9005 [main @coroutine#2]: I'm doing work
    12005 [main @coroutine#2]: I'm doing work
     */
}

// 5번의 모든 호출이 이뤄진 이유는 doCpuHardWork 함수가 suspend임에도 일시 중단 지점을 포함하지 않기 때문이다. (일반 함수나 다름없음)
// 따라서 일시 중단 함수는 스스로 취소 가능한 방법을 제공해야 한다.
// 코드가 취소 가능한 다른 함수(delay 등)를 호출하거나 코드를 취소 가능하게 하는 유틸리티 함수들을 사용하는 것이다.
