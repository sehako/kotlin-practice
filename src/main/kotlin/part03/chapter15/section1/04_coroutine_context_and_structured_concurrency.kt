package part03.chapter15.section1

import kotlinx.coroutines.*
import part03.chapter14.log

// 코루틴 컨텍스트는 구조화된 동시성 개념과 밀첩한 관련이 있으며, 이는 코루틴 간의 부모-자식 계층을 따라 상속된다.
// 새로운 코루틴을 시작할 때 코루틴 컨텍스트에서는 부모의 컨텍스트를 상속받는다.
// 그 다음 새로운 코루틴은 부모-자식 관계를 설정하는 역할을 하는 새로운 Job객체를 생성한다.
// 마지막으로 코루틴 컨텍스트에 전달된 인자가 적용된다. 이 인자들은 상속받은 값을 덮어쓸 수 있다.

fun coroutineContextExample() {
    runBlocking(Dispatchers.Default) {
        log(coroutineContext)
        launch {
            log(coroutineContext)
            launch(Dispatchers.IO + CoroutineName("IO Coroutine")) {
                log(coroutineContext)
            }
        }
    }

    /*
    0 [DefaultDispatcher-worker-1 @coroutine#1]: [CoroutineId(1),
        "coroutine#1":BlockingCoroutine{Active}@1cf01f76, Dispatchers.Default]
    6 [DefaultDispatcher-worker-2 @coroutine#2]: [CoroutineId(2),
        "coroutine#2":StandaloneCoroutine{Active}@568cace8, Dispatchers.Default]
    8 [DefaultDispatcher-worker-2 @IO Coroutine#3]: [CoroutineName(IO Coroutine), CoroutineId(3),
        "IO Coroutine#3":StandaloneCoroutine{Active}@17cc5acf, Dispatchers.IO]
     */
    // 따라서 디스패처를 별도로 지정하지 않으면 자식 코루틴은 부모 코루틴의 디스패처에서 실행된다.
    // 참고로 이 코드에서 runBlocking의 디스패처를 지정하지 않았을 때는 BlockingEventLoop가 자식 코루틴의 디스패처가 된다.
}

// 코루틴과 연관된 Job의 관계도 살펴보자.
fun coroutineJobExample() {
    runBlocking(CoroutineName("A")) {
        log("A: ${coroutineContext[Job]}")
        launch(CoroutineName("B")) {
            log("B: ${coroutineContext[Job]}")
            log("Parent(B): ${coroutineContext.job.parent}")
        }
        log("Child(A): ${coroutineContext.job.children}")
    }
    /*
    0 [main @A#1]: A: "A#1":BlockingCoroutine{Active}@200a570f
    7 [main @A#1]: Child(A): kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1@68f7aae2
    7 [main @B#2]: B: "B#2":StandaloneCoroutine{Active}@8807e25
    7 [main @B#2]: Parent(B): "A#1":BlockingCoroutine{Completing}@200a570f
     */
}

// coroutineScope역시 자체 Job 객체를 가지고 부모-자식 계층 구조를 가지게 된다.
fun coroutineScopeJobExample() {
    runBlocking {
        log("A: ${coroutineContext[Job]}")
        coroutineScope {
            log("Parent(B): ${coroutineContext.job.parent}")
            log("B: ${coroutineContext[Job]}")
            launch {
                log("Parent(C): ${coroutineContext.job.parent}")
            }
        }
    }

    /*
    0 [main @coroutine#1]: A: "coroutine#1":BlockingCoroutine{Active}@200a570f
    6 [main @coroutine#1]: Parent(B): "coroutine#1":BlockingCoroutine{Active}@200a570f
    6 [main @coroutine#1]: B: "coroutine#1":ScopeCoroutine{Active}@6073f712
    7 [main @coroutine#2]: Parent(C): "coroutine#1":ScopeCoroutine{Completing}@6073f712
     */
}
