package part03.chapter15.section1

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import part03.chapter14.log
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

// 구조화된 동시성을 통해 각 코루틴은 코루틴 스코프에 속하게 된다.
// 이는 코루틴 간의 부모-자식 관계를 확립한다.
// 코루틴 빌더 함수들은 CoroutineScope 인터페이스의 확장 함수로,
// 다른 코루틴 빌더의 본문에서 새로운 코루틴을 만들면 자식 코루틴을 만들게 되는 것이다.
fun coroutineScopeExample() {
    runBlocking {
        launch {
            delay(1.seconds)
            launch {
                delay(250.milliseconds)
                log("Grandchild coroutine")
            }
            log("Child 1 done!")
        }
        launch {
            delay(500.milliseconds)
            log("Child 2 done!")
        }
        log("Parent done!")
    }

    /*
    0 [main @coroutine#1]: Parent done!                                                                                                                                                                                                  │
    513 [main @coroutine#3]: Child 2 done!                                                                                                                                                                                               │
    1015 [main @coroutine#2]: Child 1 done!                                                                                                                                                                                              │
    1266 [main @coroutine#4]: Grandchild coroutine
     */

    // runBlocking 함수 본문이 바로 실행이 끝났어도 모든 자식 코루틴이 완료될 때까지 프로그램이 종료되지 않는다.
    // 이는 구조화된 동시성 덕분이다. 코루틴 간에는 부모-자식 관계가 있으므로 runBlocking은 모든 작업의 완료를 기다린다.
    //
}
