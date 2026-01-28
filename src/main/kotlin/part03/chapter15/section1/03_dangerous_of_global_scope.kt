package part03.chapter15.section1

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import part03.chapter14.log
import kotlin.time.Duration.Companion.milliseconds

// GlobalScope는 전역 수준에 존재하는 코루틴 스코프다. 이를 사용하려면 구조화된 동시성이 제공하는 모든 이점을 포기해야 한다.
// 전역 범위에서 시작된 코루틴은 자동으로 취소되지 않으며, 생명주기에 대한 개념도 없기 때문이다. 즉, 자원 누수의 위험이 크다.
fun globalScopeExample() {
    runBlocking {
        GlobalScope.launch {
            delay(1000.milliseconds)
            launch {
                delay(250.milliseconds)
                log("Child coroutine")
            }
            log("Child 1 done")
        }

        GlobalScope.launch {
            delay(500.milliseconds)
            log("Child 2 done")
        }
        log("Parent done")
    }
    // 0 [main @coroutine#1]: Parent done 만 출력된다.
}
// GlobalScope를 사용함으로써  계층 구조가 깨져 즉시 종료된다.
// GlobalScope.launch 함수는 lunBlocking에 소속된 코루틴이 아니게 되고, 따라서 프로그램이 즉시 종료되는 것이다.

// 라이브러리에서는 GlobalScope를 @DelicateCoroutineApi 어노테이션과 함께 선언된다.
// 이는 해당 API에 대한 충분한 이해를 요구한다는 의미다.
// 애플리케이션의 전체 생명주기동안 활성 상태를 유지해야 하는 최상위 백그라운드 프로세스같은 상황을 제외하고는
// GlobalScope를 사용해야 하는 경우는 매우 드물다.
