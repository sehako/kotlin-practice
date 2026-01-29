package part03.chapter16

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// 상태 플로우는 변수의 상태 변화를 쉽게 추적할 수 있는 공유 플로우의 특별한 버전이다.
// 이를 생성하는 방법은 공유 플로우를 생성하는 것과 비슷하다.
class ViewCounter {
    private val _counter = MutableStateFlow(0)
    val counter = _counter.asStateFlow()

    // (코드 1)
    fun increment() {
        _counter.value++
    }

    // (코드 2
    fun incrementThreadSafety() {
        _counter.update { it + 1 }
    }
}

fun stateFlowExample() {
    val vc = ViewCounter()
    vc.increment()
    // value 속성으로 현재 상태 값에 접근할 수 있다.
    println(vc.counter.value) // 1
}

fun stateFlowThreadSafetyExample() {
    val vc = ViewCounter()
    runBlocking(Dispatchers.Default) {
        repeat(10_000) {
            // value 속성이 가변이므로 단순한 연산자로 구현(코드 1 참고)할 수 있지만 이 방식은 원자적이지 않다.
//            launch { vc.increment() }

            // 이를 해결하기 위해서 상태 플로우는 원자적으로 값을 계산할 수 있는 update 함수를 제공한다. (코드 2 참고)
            launch { vc.incrementThreadSafety() }
        }
    }

    println(vc.counter.value) // 코드 1 일 때 3390
}

