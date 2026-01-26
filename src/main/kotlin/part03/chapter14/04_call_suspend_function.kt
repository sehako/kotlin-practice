package part03.chapter14

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds

// 일시 중단 함수는 일시 중단 함수 안에서만 호출할 수 있다.
// 일반적인 일시 중단 코드가 아닌 일시 중단 함수를 호출하려고 한다면 코루틴 빌더 함수를 사용해야 한다.

// 코루틴은 일시 중단 가능한 계산의 인스턴스다. 이는 다른 코루틴들과 동시에 실행될 수 있는 코드 블록이다.
// 코루틴을 생성할 때는 코루틴 빌더 함수 중 하나를 사용한다. 코루틴 빌더 함수는 다음과 같다.
// - runBlocking: 블로킹 코드와 일시 중단 함수를 연결
// - launch: 값을 반환하지 않는 새로운 코루틴을 시작
// - async: 비동기적으로 값을 계산

// runBlocking
suspend fun doSomethingSlowly() {
    delay(500.milliseconds)
    println("Done!")
}

fun nonSuspendFunction() {
    // 새 코루틴을 생성 및 실행하며, 해당 코루틴이 완료될 때 까지 현재 스레드를 블록시킨다.
    runBlocking { doSomethingSlowly() }
    // 코루틴을 사용하는 이유가 논블로킹 때문인데 runBlocking을 사용하는 이유는
    // 실제로 하나의 스레드를 블로킹하지만 코루틴 안에서는 추가적인 자식 코루틴을 얼마든지 시작할 수 있고,
    // 이 자식 코루틴들은 다른 스레드를 더 이상 블록시키지 않는다.
    // 일시 중단될 때마다 하나의 스레드가 해방되 다른 코루틴이 코드를 실행할 수 있게 된다.
}

// launch
// 새로운 자식 코루틴을 시작하는데 사용된다. 발사 후 망각 시나리오에 사용되기 때문에
// 어떤 코드를 실행하되 그 결과를 기다리지 않는 경우에 적합하다.
private var zeroTime = System.currentTimeMillis()
fun log(message: Any?) = println("${System.currentTimeMillis() - zeroTime} [${Thread.currentThread().name}]: $message")

// 이 예제를 -Dkotlinx.coroutines.debug JVM 옵션과 함께 실행하면
// 스레드 이름 옆에 코루틴 이름에 대한 추가 정보를 얻을 수 있다.
fun runConcurrently() = runBlocking {
    log("첫 번째, 부모 코루틴 시작")
    launch {
        log("두 번째 코루틴 실행되고 일시 정지 됨")
        delay(100.milliseconds)
        log("두 번째 코루틴 실행 재개됨")
    }
    launch {
        log("세 번째 코루틴이 그 사이에 실행될 수 있음")
    }
    log("첫 번째 코루틴이 두 개의 코루틴을 실행시킴")

    /*
    25 [main @coroutine#1]: 첫 번째, 부모 코루틴 시작
    31 [main @coroutine#1]: 첫 번째 코루틴이 두 개의 코루틴을 실행시킴
    32 [main @coroutine#2]: 두 번째 코루틴 실행되고 일시 정지 됨
    36 [main @coroutine#3]: 세 번째 코루틴이 그 사이에 실행될 수 있음
    137 [main @coroutine#2]: 두 번째 코루틴 실행 재개됨
     */
    // 이 코드에서는 3개의 코루틴이 시작된다. runBlocking에 의해 시작된 부모 코루틴
    // 그리고 launch 호출로 인한 두 개의 자식 코루틴이다.
    // coroutine#2가 delay를 호출하면서 코루틴이 일시 중단되었는데, 이를 일시 중단 지점이라고 한다.
    // 현재는 모든 코루틴이 같은 스레드에서 실행된 상태이지만, 나중에 다중 스레드 디스패처를 이용하면 병렬로 다룰 수도 있다.
}

// launch는 일반적으로 파일이나 데이터베이스에 쓰는 작업에 더 적합하다.
// 또한 launch는 Job 타입의 객체를 반환하는데, 이는 시작된 코루틴에 대한 제어 객체이다.

// async
// 비동기 계산을 수행할 때 사용하는 함수이다. 반환 타입은 Deferred<T> 인스턴스로 계산 결과를 기다리게 된다.
suspend fun slowlyAddNumbers(a: Int, b: Int): Int {
    log("$a + $b 결과 기다리는 중...")
    delay(100.milliseconds * a)
    return a + b
}

fun asyncCalculate() = runBlocking {
    log("비동기 계산 실행")
    // async 호출마다 새 코루틴 시작
    val myFirstDeferred = async { slowlyAddNumbers(2, 2) }
    val mySecondDeferred = async { slowlyAddNumbers(4, 4) }
    log("Deferred 결과 활성화 까지 기다리는 중...")
    log("첫 번째 결과: ${myFirstDeferred.await()}")
    log("두 번째 결과: ${mySecondDeferred.await()}")

    /*
    23 [main @coroutine#1]: 비동기 계산 실행
    29 [main @coroutine#1]: Deferred 결과 활성화 까지 기다리는 중...
    33 [main @coroutine#2]: 2 + 2 결과 기다리는 중...
    35 [main @coroutine#3]: 4 + 4 결과 기다리는 중...
    242 [main @coroutine#1]: 첫 번째 결과: 4
    440 [main @coroutine#1]: 두 번째 결과: 8
     */
    // async 호출마다 새로운 코루틴이 시작되면서 두 계산이 동시에 일어나게 했다.
    // launch와 마찬가지로 async를 호출한다고 코루틴이 일시 중단되는 것이 아니다.
    // await를 호출하면 Deferred에서 결과값이 사용 가능해질 때까지 루트 코루틴이 일시 중단된다.

    // Deferred 타입은 아직 사용할 수 없는 값을 나타낸다. 즉, 이는 연기된 계산 결과값을 나타낸다.
    // 코틀린이 다른 언어와 차별화되는 점 중 하나는 기본적인 코드에서 일시 중단 함수를 순차적으로 호출할 때는
    // async와 await를 사용할 필요가 없다는 것이다. 코틀린에서는 독립적인 작업을 동시에 실행 후
    // 그 결과를 기다릴 때만 async를 사용하면 된다.
}

/*
코루틴 빌더와 사용법을 정리하면 다음과 같다.
| 빌더         | 반환값           | 쓰임새                              |
| runBlocking | 람다가 계산한 값   | 블로킹 코드와 넌블로킹 코드 사이를 연결     |
| launch      | Job            | 발사 후 망각 코루틴 시작 (부수 효과가 있음) |
| async       | Deferred<T>    | 값을 비동기로 계산 (값을 기다릴 수 있음)    |
 */
