package part03.chapter16

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds

// 공유 플로우와 상태 플로우 모두 핫 플로우로 구독자 유무와 관계 없이 값을 발행한다.
// 하지만 두 플로우는 사용 방식이 다르다. 일반적으로 상태 플로우는 공유 플로우보타 더 간단한 API를 제공한다.
// 반면에 공유 플로우는 발행이 예상되는 시점에 구독자가 존재한다는 사실을 보장하는 책임이 온전히 개발자에게 있다.
// 따라서 공유 플로우를 사용하는 부분을 상태 플로우로 변환해 해결할 수 있는지 생각해볼만 하다.

// 예를 들어 여러 구독자에게 메시지를 브로드캐스트하는 클래스를 만들 때 공유 플로우를 사용할 수 있다고 생각할 것이다.
class Broadcaster {
    // 공유 플로우
//    private val _message = MutableSharedFlow<String>()
//    val message = _message.asSharedFlow()
    // 상태 플로우
    private val _message = MutableStateFlow<List<String>>(emptyList())
    val message = _message.asStateFlow()

    fun beginBroadcasting(scope: CoroutineScope) {
        scope.launch {
            // 공유 플로우
//            _message.emit("Hello")
//            _message.emit("World")
//            _message.emit("!")
            // 상태 플로우
            _message.update { it + "Hello" }
            _message.update { it + "World" }
            _message.update { it + "!" }
        }
    }
}

// 위 클래스에서 공유 플로우를 사용할 경우 메시지가 브로드캐스트된 다음에 구독자가 나타나면 구독자는 이미 브로드캐스트된 메시지를 받을 수 없다.
// 공유 플로우의 replay 캐시를 조정할 수도 있지만 더 간단한 추상화인 상태 플로우를 선택할 수도 있다.
// 전체 메시지 기록을 리스트로 저장하면서 구독자가 모든 이전 메시지에 쉽게 접근할 수 있게 한다.
fun stateFlowBroadcastExample() {
    runBlocking {
        val broadcaster = Broadcaster()
        broadcaster.beginBroadcasting(this)
        delay(200.milliseconds)
        broadcaster.message.collect { println("Message: $it") }
        // 상태 플로우일 때 -> Message: [Hello, World, !]
    }
}

/*
플로우의 주요 속성을 정리했다.
| 콜드 플로우 | 핫 플로우 |
|------------|-----------|
| 기본적으로 비활성(수집자에 의해 활성화됨) | 기본적으로 활성화됨 |
| 수집자가 하나 있음 | 여러 구독자가 있음 |
| 수집자는 모든 배출을 받음 | 구독자는 구독 시작 시점부터 배출을 받음 |
| 보통은 완료됨 | 완료되지 않음 |
| 하나의 코루틴에서 배출 발생 (channelFlow 사용 시 예외) | 여러 코루틴에서 배출할 수 있음 |
*/
// 또한 다양한 반응형 스트림 구현(프로젝트 리액터, RxJava 등)을 이미 사용하고 있다면
// 코틀린 플로우로 변환하거나 그 반대로 변환할 수 있는 내장 변환 함수를 사용할 수 있다.
// https://kotlinlang.org/api/kotlinx.coroutines/ 참고
