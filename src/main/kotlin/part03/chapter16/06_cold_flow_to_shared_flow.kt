package part03.chapter16

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import part03.chapter14.log
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds


// 온도 센서로부터 값을 수집하는 플로우를 만들어보자.
fun querySensor(): Int = Random.nextInt(-10, 30)

fun getTemperatures(): Flow<Int> {
    return flow {
        while (true) {
            emit(querySensor())
            delay(500.milliseconds)
        }
    }
}

// getTemperatures를 여러 번 호출한다면 콜드 플로우이므로 각 수집자가 센서에 독립적인 질의를 하게 된다.
// 이 외에도 네트워크 요청이네 데이터베이스 쿼리를 실행할 때
// 불필요한 외부 시스템과의 상호작용이나 장시간 연산은 피해야 한다. 이런 경우 변환된 플로우를 여러 수집자가 공유해야 한다.
// 이때 shareIn 함수를 사용하면 주어진 콜드 플로우를 한 플로우인 공유 플로우로 전환할 수 있다.
fun sharedFlowFromColdFlowExample() {
    val temps = getTemperatures()
    runBlocking {
        // 콜드 플로우를 공유된 핫 플로우로 전환
        val sharedTemps = temps.shareIn(scope = this, started = SharingStarted.Lazily)
        // started는 플로우가 실제로 언제 시작돼야 하는 지 정의한다.
        /*
        - Eagerly: 플로우 수집 즉시 시작
        - Lazily: 첫 번째 구독자가 나타나야만 수집 시작하고 멈추지 않음
        - WhileSubscribed: 첫 번째 구독자가 나타나야 수집을 시작, 마지막 구독자가 사라지면 플로우 수집 취소
         */
        launch {
            sharedTemps.collect { log("$it C") }
        }

        launch {
            sharedTemps.collect { log("$it F") }
        }
    }
}

// 코틀린에서는 콜드 플로우로 노출하고, 필요할 때 이를 핫 플로우로 변환하는 패턴이 자주 사용된다.
// sharedIn은 코루틴 스코프를 통해 구조적 동시성에 참여하브로
// 공유 플로우를 둘러싼 코루틴 스코프가 취소될 때 공유 플로우 내부 로직도 자동으로 취소된다.
