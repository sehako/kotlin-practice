package part03.chapter15.section1

import kotlinx.coroutines.*
import part03.chapter14.log
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

// 코루틴 빌더를 사용해 새로운 코루틴을 만들면 이는 자체적인 CoroutineScope를 생성한다.
// 하지만 새로운 코루틴을 만들지 않고도 코루틴 스코프를 그룹화할 수 있는데, 이때 coroutineScope 함수를 사용한다.
// 이 일시 중단 함수는 일반적으로 여러 코루틴을 활용해 계산을 수행하는 동시적 작업 분해에 사용된다.
suspend fun generateValue(): Int {
    delay(500.milliseconds)
    return Random.nextInt(0, 10);
}

suspend fun computeSum() {
    log("Computing a sum...")
    val sum = coroutineScope { // 새로운 코루틴 스코프 생성
        val a = async { generateValue() }
        val b = async { generateValue() }
        a.await() + b.await() // 자식 코루틴이 끝날때까지 대기
    }

    log("Sum = $sum")

    /*
    0 [main @coroutine#1]: Computing a sum...
    519 [main @coroutine#1]: Sum = 3
     */
}

// 또한 구체적인 생명주기를 정의하고, 동시 처리나 코루틴의 시작과 종료를 관리하는 클래스를 만들 때에도
// CoroutineScope 생성자 함수를 사용해 새로운 독자적인 코루틴 스코프를 생성할 수 있다.
// 이 함수는 실행을 일시 중단 하지 않으며, 새로운 코루틴을 시작할 때 쓸 수 있는 새로운 코루틴 스코프를 생성하기만 한다.
// CoroutineScope는 코루틴 스코프와 연관된 코루틴 스코프를 파라미터로 받는다.
class ComponentWithScope(dispatcher: CoroutineDispatcher = Dispatchers.Default) {
    // 디스패처만으로 호출하면 새로운 Job이 자동으로 생성되지만, 실무에서는 SupervisorJob과 함께 사용된다.
    // SupervisorJob은 동일한 영역과 관련된 다른 코루틴을 취소하지 않고, 처리되지 않은 예외를 전파하지 않게 해주는 특수한 Job이다.
    private val scope = CoroutineScope(dispatcher + SupervisorJob())

    fun start() {
        log("Starting")
        scope.launch {
            while (true) {
                delay(500.milliseconds)
                log("Component working")
            }
        }

        scope.launch {
            log("Doing a one-off task")
            delay(500.milliseconds)
            log("Done")
        }
    }

    fun stop() {
        log("Stopping")
        scope.cancel()
    }
}

fun coroutineScopeComponentExample() {
    val component = ComponentWithScope()
    component.start()
    Thread.sleep(2.seconds.inWholeMilliseconds)
    component.stop()
    /*
    0 [main]: Starting
    16 [DefaultDispatcher-worker-2 @coroutine#2]: Doing a one-off task
    520 [DefaultDispatcher-worker-1 @coroutine#2]: Done
    520 [DefaultDispatcher-worker-2 @coroutine#1]: Component working
    1024 [DefaultDispatcher-worker-1 @coroutine#1]: Component working
    1530 [DefaultDispatcher-worker-1 @coroutine#1]: Component working
    2019 [main]: Stopping
     */
    // stop 호출로 컴포넌트의 생명주기가 종료된다.
}

/*
coroutineScope: 작업을 동시성으로 실행하기 위해 분해할 때 사용하는 일시 중단 함수다.
여러 코루틴을 시작하고, 완료될 때까지 기다리며, 결과를 계산할 수 있다.

CoroutineScope: 코루틴을 클래스의 생명주기와 연관시키는 영역을 생성할 때 쓰인다.
영역을 생성하고 즉시 반환하여 나중에 사용 및 취소할 수 있다.

실무에서는 coroutineScope 일시 중단 함수가 많이 사용된다고 한다.
 */
