package part03.chapter16

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import part03.chapter14.log
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

// 핫 플로우는 여러 구독자라고 불리는 수집자들이 발행된 항목을 공유한다.
// 이는 시스템에서 이벤트나 상태 변경이 발생하여 수집자가 존재하는지 여부에 상관없이 값을 발행해야 하는 경우에 적합하다.
// 코틀린 코루틴에는 2개의 핫 플로우 구현이 기본적으로 제공된다.
/*
- 공유 플로우: 값을 브로드캐스트하기 위해 사용된다.
- 상태 플로우: 상태를 전달하는 특별한 경우에 사용된다.
 */

// 일반적으로 공유 플로우는 컨테이너 클래스 내에 선언된다.
class RadioStation {
    // 가변 공유 플로우를 비공개 프로퍼티로 정의
//    private val _messageFlow = MutableSharedFlow<Int>()
    private val _messageFlow = MutableSharedFlow<Int>(replay = 5) // (코드 1) 최신 5개의 값을 재생

    // 공유 플로우에 대한 읽기 전역 뷰 제공
    val messageFlow = _messageFlow.asSharedFlow()

    fun beginBroadcasting(scope: CoroutineScope) {
        scope.launch {
            while (true) {
                delay(500.milliseconds)
                val number = Random.nextInt(0, 10)
                log("Emitting $number")
                _messageFlow.emit(number)
            }
        }
    }
}

// 인스턴스 생성 후 beginBroadcasting을 호출하면 구독자가 없어도 브로드캐스트가 즉시 시작된다.
fun broadcastWithoutSubscribersExample() {
    runBlocking {
        RadioStation().beginBroadcasting(this) // runBlocking의 코루틴 스코프에서 코루틴 시작
    }

    /*
    0 [main @coroutine#2]: Emitting 6
    511 [main @coroutine#2]: Emitting 2
    1016 [main @coroutine#2]: Emitting 4
    ...
     */
}

// 구독자를 추가하는 방법은 collect를 호출하는 것이다.
// 이때 구독자는 구독 시작 이후에 발행된 값만 수신한다는 점에 유의해야 한다.
fun sharedFlowSubscribeExample() {
    runBlocking {
        val station = RadioStation()
        station.beginBroadcasting(this)
        delay(600.milliseconds)
        launch {
            station.messageFlow.collect { log("Collected $it By A") }
        }

        // 같은 플로우를 구독하는 다른 구독자를 추가할 수도 있다.
        launch {
            station.messageFlow.collect { log("Collected $it By B") }
        }
    }

    /*
    0 [main @coroutine#2]: Emitting 3
    512 [main @coroutine#2]: Emitting 8
    515 [main @coroutine#3]: Collected 8 By A
    516 [main @coroutine#4]: Collected 8 By B
     */
}

// 공유 플로우 구독자는 구독을 시작한 이후에 발행된 값만 수신한다.
// 그 이전에 발행된 원소도 수신하기를 원한다면 MutableSharedFlow 생성 시 replay 파라미터를 사용할 수 있다.
// 이는 새로운 구독 이벤트시 해당 구독자에게 제공할 값의 캐시이다. (코드 1 참고)
fun main() {
    runBlocking {
        val station = RadioStation()
        station.beginBroadcasting(this)
        delay(2.seconds)
        station.messageFlow.collect { log("Collected $it") }
    }

    /*
    0 [main @coroutine#2]: Emitting 6
    520 [main @coroutine#2]: Emitting 0
    1023 [main @coroutine#2]: Emitting 5
    1487 [main @coroutine#1]: Collected 6
    1487 [main @coroutine#1]: Collected 0
    1487 [main @coroutine#1]: Collected 5
    1529 [main @coroutine#2]: Emitting 9
    1529 [main @coroutine#1]: Collected 9
    ...
     */
}
