package part03.chapter16

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds

// 콜드 플로우를 제공하는 API와 작업할 때 stateIn을 사용해 콜드 플로우를 상태 플로우로 변환할 수 있다.
// 이러면 원래 플로우에서 발행된 최신 값을 항상 읽을 수 있다.
// 또한 공유 플로우처럼 여러 수집자를 추가하거나 value 속성에 접근해도 업스트림 플로우(값의 재발행을 의미)는 실행되지 않는다.
fun stateFlowFromColdFlowExample() {
    val temps = getTemperatures()
    runBlocking {
        val tempState = temps.stateIn(scope = this)
        tempState.collect {
            println(it)
            delay(800.milliseconds)
            println(it)
        }
    }
}
