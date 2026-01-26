package part03.chapter14

// 코루틴은 코드의 형태가 여전히 순차적으로 보인다는 것이 다른 동시성 접근 방식과의 핵심 차이점이다.
// 이것이 가능한 이유는 일시 중단 함수가 있기 때문이다.

fun login(credentials: String): String {
    return "User-1"
}

fun loadUserData(userId: String): String {
    return "user-nickname"
}

fun showData(nickname: String) {
    println(nickname)
}

fun showUserInfo(credentials: String) {
    // login과 loadUserData는 네트워크 요청으로 인해 블록되는 함수들이다.
    val userId = login(credentials)
    val nickname = loadUserData(userId)
    showData(nickname)
}
// 위 작업은 대부분의 시간을 네트워크 작업 결과를 기다리기 때문에 스레드가 블록된다.
// 블록된 스레드는 자원을 낭비하게되고, 서비스가 처리할 수 있는 요청의 수에 한계가 생기거나 UI가 멈추게 될 수 있다.

// 일시 중단 함수는 이 문제를 개선하는데 도움을 줄 수 있다.
suspend fun suspendLogin(credentials: String): String {
    return "User-1"
}

suspend fun suspendLoadUserData(userId: String): String {
    return "user-nickname"
}

suspend fun suspendShowUserData(credentials: String) {
    val userId = suspendLogin(credentials)
    val nickname = suspendLoadUserData(userId)
    showData(nickname)
}
// 함수에 suspend 변경자를 선언하면 이는 함수가 실행을 잠시 멈출 수도 있다는 의미가 된다.
// 일시 중단은 기저 스레드를 블록시키지 않고 다른 코드가 같은 스레드에서 실행될 수 있다.
// 여전히 순차적으로 실행되지만 일시 중단된 사이에 기저 스레드는 다른 작업을 진행할 수 있다.
