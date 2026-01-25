package chapter14

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

// 코루틴은 스레드 위에 만들어진 추상화다. runBlocking을 사용할 경우 코드는 함수를 호출한 스레드에서 실행된다.
// 디스패처를 사용하면 코드가 어떤 스레드에서 실행될지 세밀하게 제어할 수 있다.
// 이를 통해 코루틴을 특정 스레드로 제한하거나 스레드 풀에 분산시킬 수 있으며,
// 코루틴이 한 스레드에서만 실행될지 여러 스레드에서 실행될지 결정할 수 있다.
// 코루틴은 한 스레드에서 실행을 일시 중단하고 디스패처가 지시하는 대로 다른 스레드에서 실행을 재개할 수 있다.

// 디스패처 선택
// 코루틴은 기본적으로 부모 코루틴에서 디스패처를 상속 받기 때문에 모든 코루틴에 대해 명시적으로 디스패처를 저장할 필요는 없다.
// 하지만 선택할 수 있는 디스패처들이 있다.
/*
디스패처                   | 스레드 개수                          | 쓰임새
Dispatchers.Default      | CPU 코어 수                         | 일반적인 연산, CPU 집약적인 작업
Dispatchers.Main         | 1                                 | UI 프레임워크 맥락에서 UI 작업 ("UI 스레드")
Dispatchers.IO           | 64 + CPU 코어 개수                  | 블로킹 IO 작업, 네트워크 작업, 파일 작업
                         | (최대 64개 병렬 실행)                 |
Dispatchers.Unconfined   | … ("아무 스레드나")                  | 즉시 스케줄링해야 하는 특수한 경우 (일반적인 용도는 아님)
limitedParallelism(n)    | 커스텀(n)                           | 커스텀 시나리오

Unconfined는 특정 스레드에 제약되지 않고 코루틴이 실행되며,
디스패처에 대한 병렬성 제약을 정의해야 하는 경우에는 limitedParallelism(n) 함수를 사용한다.
 */

// 코루틴을 특정 디스패처에서 실행하기 위해 코루틴 빌더 함수에게 디스패처를 인자로 전달할 수 있다.
fun coroutineBuilderWithDispatcher() {
    runBlocking {
        log("runBlocking...")
        launch(Dispatchers.Default) {
            log("launch on Default dispatcher...")
        }
    }

    /*
    0 [main @coroutine#1]: runBlocking...
    6 [DefaultDispatcher-worker-1 @coroutine#2]: launch on Default dispatcher...
     */
}

// withContext로 코루틴 전체의 디스패처를 바꾸지 않고 어떤 부분이 어디서 실행될지 세밀하게 제어할 수도 있다.
// UI 프레임워크를 다룰 때 코드가 특정 스레드에서 실행되도록 보장해야 할 때가 있다.
// 이를 위해 백그라운드에서 연산을 수행하고 결과가 준비되면 UI 스레드로 전환해 랜더링하는 것이다.
fun withContextExample() {
    runBlocking {
        launch(Dispatchers.Default) {
            // 백그라운드 작업 실행
            withContext(Dispatchers.Main) {
                // 디스패처를 UI 스레드로 전환하여 랜더링 수행
            }
        }
    }
}



