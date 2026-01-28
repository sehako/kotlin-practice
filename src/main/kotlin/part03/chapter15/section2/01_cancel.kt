package part03.chapter15.section2

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import part03.chapter14.log
import kotlin.time.Duration.Companion.milliseconds

// 애플리케이션에서 취소의 이점은 다음과 같다.
/*
- 불필요한 작업 방지
- 자원 누주 방지
- 오류 처리
 */
// 코틀린 코루틴은 취소를 처리하는 내장 메커니즘을 제공한다.
fun cancelExample() {
    runBlocking {
        val launchedJob = launch {
            log("I'm launched")
            delay(1000.milliseconds)
            log("I'm done")
        }

        val asyncDeferred = async {
            log("I'm async")
            delay(1000.milliseconds)
            log("I'm done")
        }

        delay(200.milliseconds)
        launchedJob.cancel()
        asyncDeferred.cancel()
    }
    /*
    0 [main @coroutine#2]: I'm launched
    5 [main @coroutine#3]: I'm async
     */
    // 각 코루틴 스코프의 코루틴 컨텍스트에도 Job이 포함되어 있기 때문에 이를 사용해 영역을 취소할 수 있다.
}
