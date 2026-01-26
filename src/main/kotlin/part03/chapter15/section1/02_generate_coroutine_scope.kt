package part03.chapter15.section1

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import part03.chapter14.log
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

// 코루틴 빌더를 사용해 새로운 코루틴을 만들면 이는 자체적인 CoroutineScope를 생성한다.
// 하지만 새로운 코루틴을 만들지 않고도 코루틴 스코프를 그룹화할 수 있는데, 이때 coroutineScope 함수를 사용한다.
// 이 일시 중단 함수는 일반적으로 여러 코루틴을 활용해 계산을 수행하는 동시적 작업 분해에 사용된다.
suspend fun generateValue(): Int {
    delay(500.milliseconds)
    return Random.nextInt(0, 10);
}

suspend fun computeSum() {
    log("Computing a sum...")
    val sum = coroutineScope { // 새로운 코루틴 스코프 생성
        val a = async { generateValue() }
        val b = async { generateValue() }
        a.await() + b.await() // 자식 코루틴이 끝날때까지 대기
    }

    log("Sum = $sum")

    /*
    0 [main @coroutine#1]: Computing a sum...
    519 [main @coroutine#1]: Sum = 3
     */
}

// 또한 구체적인 생명주기를 정의하고, 동시 처리나 코루틴의 시작과 종료를 관리하는 클래스를 만들 때에도
// CoroutineScope 생성자 함수를 사용해 새로운 독자적인 코루틴 스코프를 생성할 수 있다.
// 이 함수는 실행을 일시 중단 하지 않으며, 새로운 코루틴을 시작할 때 쓸 수 있는 새로운 코루틴 스코프를 생성하기만 한다.

