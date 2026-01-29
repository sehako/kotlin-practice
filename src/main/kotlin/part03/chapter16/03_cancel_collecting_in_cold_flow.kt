package part03.chapter16

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.seconds

// 코루틴의 취소 메거니즘이 플로우 수집자에게도 적용된다.
fun counterFlow(): Flow<Int> {
    return flow {
        for (i in 1..Int.MAX_VALUE) {
            emit(i)
            delay(1.seconds)
        }
    }
}

fun cancelCollectingInColdFlowExample() {
    runBlocking {
        val collector = launch {
            counterFlow().collect {
                println(it)
            }
        }

        delay(5.seconds)
        collector.cancel()
    }
}

// 콜드 플로우의 내부 구현을 살펴보도록 하자. 코틀린의 콜드 플로우는 일시 중단 함수와 수신 객체 지정 람다를 조합해서 만들어졌다.
// Flow와 FlowCollector라는 두 가지 인터페이스가 필요하다.
/*
public interface Flow<out T> {
    public suspend fun collect(collector: FlowCollector<T>)
}

public fun interface FlowCollector<in T> {
    public suspend fun emit(value: T)
}
 */

// 이를 통해 flow 빌더 함수를 사용해 콜드 플로우를 정의할 때 제공된 람다의 수신 객체 타입은 FlowCollector다.
// 이 때문에 빌더 내에서 emit 함수를 호출할 수 있다. emit 함수는 collect 함수에 전달된 람다를 호출하며,
// 결과적으로 두 람다가 서로 호출되는 구조를 가진다.
/*
- collect를 호출하면 플로우 빌더 함수의 본문이 실행된다.
- 이 코드가 emit을 호출하면 emit에 전달된 파라미터로 collect에 전달된 람다가 호출된다.
- 람다 표현식이 실행을 완료하면 함수는 빌더 함수의 본문으로 돌아가 계속 실행된다.
 */

