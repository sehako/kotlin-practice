package chapter14

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration.Companion.seconds

// 코루틴과 디스패처를 이용하는 것은 스레드 안전을 보장해주지 않는다는 것이다.
// 한 코루틴은 항상 순차적으로 실행되어 스레드 안전하지만,
// 여러 코루틴이 동일한 데이터에 접근하면 스레드 안전은 깨지게 된다.
fun coroutineThreadUnSafeExample() {
    runBlocking {
        var x = 0
        repeat(1000) {
            launch(Dispatchers.Default) {
                x++
            }
        }
        delay(1.seconds)
        println(x)
    }

    // 994
}
// 여러 코루틴이 같은 데이터를 수정하고 있기 때문에 다중 스레드 디스패처에서 실행되면 일부 증가 작업이 덮어씌워지게 된다.
// 즉, 코루틴을 사용하면 스레드를 사용할 때와 같이 동시성 문제가 발생하게 된다.

// 스레드 안전을 보장하기 위한 몇 가지 방법이 있다.
// 코루틴은 Mutex 잠금을 통해 코드 임계 영역이 한 번에 하나의 코루틴만 실행되게 보장할 수 있다.
fun mutexExample() {
    runBlocking {
        val mutex = Mutex()
        var x = 0
        repeat(10_000) {
            launch(Dispatchers.Default) {
                mutex.withLock {
                    x++
                }
            }
        }
    }
}

// 또한 AtomicInteger나 ConcurrentHashMap 같은 병렬 변경을 위한 스레드 안전한 자료구조를 사용할 수도 있다.
// 코루틴을 단일 스레드 디스패처에서 실행되도록 제한하는 방법도 있지만, 이는 성능을 고려해야 한다.
// 관련 내용은 https://kotlinlang.org/docs/shared-mutable-state-and-concurrency.html를 참고하도록 하자.
