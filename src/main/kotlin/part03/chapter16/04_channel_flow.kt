package part03.chapter16

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import part03.chapter14.log
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

// flow를 사용해 만든 콜드 플로우는 순차적으로 실행된다.
// 코드 블록은 일시 중단 함수의 본문처럼 하나의 코루틴으로 실행된다.
// 이러한 순차적 특성은 병목이 될 수 있다.
suspend fun getRandomNumber(): Int {
    delay(500.milliseconds)
    return Random.nextInt()
}

fun randomNumbersFlow() = flow {
    repeat(10) {
        emit(getRandomNumber())
    }
}

fun coldFlowSequenceExample() {
    runBlocking {
        randomNumbersFlow().collect { log(it) }
    }

    /*
    0 [main @coroutine#1]: 1229950973
    ...
    4038 [main @coroutine#1]: -904929420
    4539 [main @coroutine#1]: -1514187873
     */
}

// 플로우는 순차적으로 실행되며, 모든 계산은 동일한 코루틴에서 실행된다.
// 플로우 빌더에서 백그라운드 코루틴을 실행하면 해결된다고 생각하지만
// 콜드 플로우는 같은 코루틴 내에서만 emit을 호출할 수 있다.

// 이때 여러 코루틴에서 값 생산을 허용하는 동시성 플로우를 작성하는 채널 플로우를 사용한다.
// channelFlow의 람다는 새로운 백그라운드 코루틴을 시작할 수 있는 코루틴 스코프를 제공한다.
fun randomNumbersChannelFlow() = channelFlow { // 새 채널 플로우 생성
    repeat(10) {
        launch {
            send(getRandomNumber()) // 여러 코루틴에서 send를 호출할 수 있다.
        }
    }
}

fun channelFlowExample() {
    runBlocking {
        randomNumbersChannelFlow().collect { log(it) }
    }

    /*
    0 [main @coroutine#1]: 2002993323
    ...
    7 [main @coroutine#1]: 1492603228
    7 [main @coroutine#1]: -1211385742
     */
}

// 일반적으로 콜드 플로우는 가장 간단하고 성능이 좋은 추상화다.
// 콜드 플로우는 엄격하게 순차적으로 실행되기에 새로운 코루틴을 시작할 수 없지만 아주 쉽게 생성할 수 있고,
// 인터페이스가 한 가지 함수로 구성되며, 관리해야 할 추가적인 원소가 오버헤드가 없다.

// 반면, 채널 플로우는 동시 작업을 위해 설계되었다.
// 내부적으로 동시성 기본 요소인 채널을 관리해야 하기 때문에 생성하는 데 비용이 든다.
// 따라서 플롱웨서 새로운 코루틴을 시작해야 하는 경우에만 채널 플로우를 선택하도록 하자.
