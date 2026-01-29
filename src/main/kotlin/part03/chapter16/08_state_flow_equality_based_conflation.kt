package part03.chapter16

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import part03.chapter14.log
import kotlin.time.Duration.Companion.milliseconds

// 상태 플로우 역시 collect를 호출하여 시간에 따라 값을 구독할 수 있다.
enum class Direction { LEFT, RIGHT }

class DirectionSelector {
    private val _direction = MutableStateFlow(Direction.LEFT)
    val direction = _direction.asStateFlow()

    fun turn(d: Direction) {
        _direction.update { d }
    }
}

// 위 상태 플로우를 collect하여 로그를 남기는 코루틴은 새로운 값이 설정될 때마다 알림을 받는다.
fun main() {
    runBlocking {
        val switch = DirectionSelector()
        launch {
            switch.direction.collect {
                log("Direction: $it")
            }
        }

        delay(200.milliseconds)
        switch.turn(Direction.RIGHT)
        delay(200.milliseconds)
        switch.turn(Direction.LEFT)
        delay(200.milliseconds)
        switch.turn(Direction.LEFT)
    }

    /*
    0 [main @coroutine#2]: Direction: LEFT
    202 [main @coroutine#2]: Direction: RIGHT
    407 [main @coroutine#2]: Direction: LEFT
     */
    // LEFT 인자를 2번 연속으로 전달했음에도 구독자가 한 번만 호출된다.
    // 이는 상태 플로우가 동등성 기반 통합을 수행하기 때문으로, 값이 실제로 달라졌을 때만 구독자에게 값을 발행한다.
}
