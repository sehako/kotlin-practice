package part03.chapter16

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import part03.chapter14.log
import kotlin.time.Duration.Companion.milliseconds

// 콜드 플로우를 생성하는 방법은 flow 함수를 사용하는 것이다.
// 빌더 함수의 블록 내에서는 emit 함수를 호출해 플로우의 수집자에게 값을 제공하고,
// 수집자가 해당 값을 처리할 때까지 빌더 합수의 실행을 중단한다.
/*
public fun <T> flow(@BuilderInference block: suspend FlowCollector<T>.() -> Unit): Flow<T> = SafeFlow(block)

private class SafeFlow<T>(private val block: suspend FlowCollector<T>.() -> Unit) : AbstractFlow<T>() {
    override suspend fun collectSafely(collector: FlowCollector<T>) {
        collector.block()
    }
}
 */
// flow는 suspend 변경자가 붙어있으므로 빌더 내부에서 delay같은 일시 중단 함수를 호출할 수 있다.
fun coldFlowExample() {
    // 연속적인 값의 스트림을 표현하는 Flow<T> 타입의 객체를 반환
    val letters: Flow<String> = flow {
        log("Emitting A")
        emit("A")
        delay(200.milliseconds)
        log("Emitting B")
        emit("B")
    }

    // 처음에 비활성 상태이며, 최종 연산자가 호출되어야지 빌더에서 정의된 계산이 시작된다.
    // flow 빌더 함수는 실제 작업이 시작되지 않기 때문에 일시 중단 코드가 아닌 곳에서도 플로우를 작성할 수 있다.
    // 실무에서는 일반 함수에서 시간이 지나면서 여러 값을 반환하는 콜드 플로우를 자주 사용한다고 한다.

    // 콜드 플로우의 실행은 collect로 이루어지며, 이는 일시 중단 함수이기 때문에 플로우가 끝날 때까지 일시 중단된다.
    // 수집자에게 제공된 람다역시 일시 중단될 수 있기 때문에 해당 람다 블록에서도 일시 중단 함수를 호출할 수 있다.
    // 콜드 플로우에서 collect를 여러 번 호출하면 그 코드가 여러 번 실행될 수 있다는 것을 알아둬야 한다.
    runBlocking {
        letters.collect { log(it) }
    }
    /*
    0 [main @coroutine#1]: Emitting A
    7 [main @coroutine#1]: A
    210 [main @coroutine#1]: Emitting B
    210 [main @coroutine#1]: B
     */
}


