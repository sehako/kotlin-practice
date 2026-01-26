package part03.chapter14

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.runBlocking

// 코루틴은 코루틴 컨텍스트에 추가적인 정보를 담고 있다.
// 앞서 코루틴 빌더 함수와 withContext 함수에 서로 다른 디스패처를 인자로 전달했다.
// 이 파라미터는 실제로는 CoroutineContext이다.

// 각 코루틴은 추가적인 문맥 정보를 담고 있는데, 이 문맥은 CoroutineContext라는 형태로 제공된다.
// 이는 CoroutineContext는 여러 요소로 이뤄진 집합이고, 이 요소 중 하나는 코루틴이 어떤 스레드에서 실행될지를 결정하는 디스패처다.
// 또한 보통 코루틴의 생명주기와 취소를 관리하는 Job 객체도 포함되며
// CoroutineName이나 CoroutineExceptionHandler 같은 추가적인 메타데이터도 있을 수 있다.

// 현재 코루틴의 코루틴 컨텍스트 확인 방법
suspend fun introspect() {
    log(currentCoroutineContext())
    /*
    0 [main @coroutine#1]: [CoroutineId(1),
            "coroutine#1":BlockingCoroutine{Active}@707f7052, BlockingEventLoop@11028347]
     */
}

// 코루틴 빌더나 withContext 함수에 인자를 전달하면 자식 코루틴의 컨텍스트에서 해당 요소를 덮어쓴다.
// 여러 파라미터를 한 번에 덮어쓰려면 + 연산자를 사용해 CoroutineContext를 결합할 수 있다.
fun combineCoroutineContext() = runBlocking(Dispatchers.IO + CoroutineName("myCoroutine")) {
    introspect()
    /*
    0 [DefaultDispatcher-worker-1 @myCoroutine#1]: [CoroutineName(myCoroutine), CoroutineId(1),
            "myCoroutine#1":BlockingCoroutine{Active}@11bc2ddc, Dispatchers.IO]
     */

    // runBlocking에게 전달한 인자는 자식 코루틴의 컨텍스트의 원소를 덮어쓴다.
    // Dispatchers.IO는 runBlocking의 특별한 디스패처인 BlockingEventLoop 디스패처를 대신하며
    // 코루틴의 이름은 MyCoroutine으로 설정된 것을 볼 수 있다.
}

