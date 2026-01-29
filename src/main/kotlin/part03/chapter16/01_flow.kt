package part03.chapter16

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import part03.chapter14.log
import kotlin.time.Duration.Companion.milliseconds

// 코틀린 플로우는 시간이 지나면서 나타나는 값과 작업할 수 있게 해주는 코루틴 기반의 추상화다.
// 플로우의 설계는 리액티브 스트림(RxJava | Project Reactor)의 철학을 따른다.
// 따라서 점진적인 로딩, 이벤트 스트림 작업, 구독 스타입 API를 모델링하는 데 사용할 수 있다.

// 원소의 생성과 함께 처리
fun createValues(): Flow<Int> {
    return flow { // flow 빌더 함수 사용
        emit(1)
        delay(1000.milliseconds)
        emit(2)
        delay(1000.milliseconds)
        emit(3)
        delay(1000.milliseconds)
    }
}

fun flowExample() {
    runBlocking {
        val flow = createValues();
        flow.collect { log(it) }
    }

    /*
    0 [main @coroutine#1]: 1
    1015 [main @coroutine#1]: 2
    2021 [main @coroutine#1]: 3
     */
}

// 플로우는 콜드 플로우와 핫 플로우라는 두 가지 카테고리로 나뉜다.
/*
콜드 플로우: 값이 실제로 소비되기 시작할 때만 값을 생성(발행)한다.
핫 플로우: 값이 실제로 소비되고 있는지와 관계 없이 값을 독립적으로 발행하며, 브로드캐스트 방식으로 동작한다.
 */
