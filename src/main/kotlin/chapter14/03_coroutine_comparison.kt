package chapter14

import reactor.core.publisher.Mono
import java.util.concurrent.CompletableFuture

// 코루틴과 콜백, Future, 반응형 스트림(Project Reactor)을 비교해보도록 하자.

fun showDataComparison(nickname: String) {
    println(nickname)
}

// 코루틴
suspend fun coroutineLogin(credentials: String): String {
    return "User-1"
}

suspend fun coroutineLoadUserData(userId: String): String {
    return "user-nickname"
}

suspend fun coroutineShowUserData(credentials: String) {
    val userId = coroutineLogin(credentials)
    val nickname = coroutineLoadUserData(userId)
    showDataComparison(nickname)
}

// 콜백
fun loginAsyncCallback(credentials: String, callback: (String) -> Unit) {
    callback("User-1")
}

fun loadUserDataAsyncCallback(userId: String, callback: (String) -> Unit) {
    callback("user-nickname")
}

fun showUserDataAsyncCallback(credentials: String, callback: (String) -> Unit) {
    loginAsyncCallback(credentials) { userId ->
        loadUserDataAsyncCallback(userId) { nickname ->
            showDataComparison(nickname)
        }
    }
}
// 콜백 구조는 콜백 함수에서 또 다른 콜백을 호출하는 콜백 지옥이 되기 쉽상이다.

// CompletableFuture
fun loginAsyncCompletableFuture(credentials: String): CompletableFuture<String> {
    return CompletableFuture.completedFuture("User-1")
}

fun loadUserDataAsyncCompletableFuture(userId: String): CompletableFuture<String> {
    return CompletableFuture.completedFuture("user-nickname")
}

fun showUserDataAsyncCompletableFuture(credentials: String) {
    loginAsyncCompletableFuture(credentials)
        .thenCompose { loadUserDataAsyncCompletableFuture(it) }
        .thenAccept { showDataComparison(it) }
}
// 이 방식을 사용하면 콜백 중첩을 피할 수 있지만, 새로운 연산자의 의미를 배워야 한다.
// 또한 이를 위해서 반환 타입을 CompletableFuture로 한 번 감싸서 전달해야 한다.

// Project Reactor
fun loginReactor(credentials: String): Mono<String> {
    return Mono.just("User-1")
}

fun loadUserDataReactor(userId: String): Mono<String> {
    return Mono.just("user-nickname")
}

fun showUserDataReactor(credentials: String) {
    loginReactor(credentials)
        .flatMap { loadUserDataReactor(it) }
        .subscribe { showDataComparison(it) }
}
// 반응형 프로그래밍 역시 함수를 선언하거나 사용할 때 새로운 연산자를 코드에 도입해야 한다.
// 반면에 코틀린의 코루틴은 suspend 변경자만 추가함으로써 코드의 순차적인 호출은 유지하면서도
// 스레드를 블록시키는 단점을 피할 수 있다.
