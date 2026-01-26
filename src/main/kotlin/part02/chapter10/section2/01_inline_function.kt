package part02.chapter10.section2

import java.util.concurrent.locks.Lock

// 코틀린은 람다를 익명 클래스로 컴파일한다.
// 일반적으로 한 번 생성된 람다가 재사용 되고, 외부 변수를 캡처한 경우에는 해당 변수 값을 필드로 가지는 새로운 객체가 매번 생성된다.
// 코틀린 컴파일러에서는 inline 변경자를 어떤 함수에 붙이면 컴파일러는
// 그 함수가 쓰이는 위치에 함수 호출을 생성하는 대신 함수를 구현하는 코드로 바꿔준다.
// 이는 즉 함수를 호출하는 코드를 함수를 호출하는 바이트코드 대신에 함수 본문을 번역한 바이트코드로 컴파일한다는 뜻이다.

// 다중 스레드 환경에서 어떤 공유 자원에 대한 동시 접근을 막기 위한 함수를 보도록 하자.
inline fun <T> synchronized(lock: Lock, action: () -> T): T {
    lock.lock()
    try {
        return action()
    } finally {
        lock.unlock()
    }
}

fun synchronizedExample(lock: Lock) {
    println("Before sync")
    synchronized(lock) {
        println("Hello, world!")
    }
    println("After sync")
}

// 위 코드는 다음 코틀린 코드와 동등해진다.
// synchronized 함수의 본문과 전달된 람다의 본문도 함께 인라이닝 되었다.
// 람다의 본문에 의해 만들어지는 바이트코드는 람다를 호출하는 코드 정의의 일부분으로 간주되기 때문에
// 코틀린 컴파일러는 그 람다를 함수 인터페이스를 구현하는 익명 클래스로 감싸지 않는다.
fun __synchronizedExample__(lock: Lock) {
    println("Before sync")
    lock.lock()
    try {
        println("Hello, world!")
    } finally {
        lock.unlock()
    }
    println("After sync")
}

// 인라인 함수를 호출하면서 람다를 호출하는 대신 함수 타입의 변수를 넘길 수도 있다.
class LockOwner(val lock: Lock) {
    fun runUnderLock(body: () -> Unit) {
        synchronized(lock, body)
    }

    // 이런 경우 인라인 함수를 호출하는 코드 위치에서는 변수에 저장된 코드 위치에서는 변수에 저장된 람다의 코드를 알 수 없다.
    // 따라서 람다 본문은 인라이닝되지 않고 synchronized 함수의 본문만 다음과 같이 인라이닝된다.
    fun __runUnderLock__(body: () -> Unit) {
        lock.lock()
        try {
            // 람다는 인라이닝 되지 않는다.
            body()
        } finally {
            lock.unlock()
        }
    }
}

// 마지막으로, 하나의 인라인 함수를 두 곳에서 각각 다른 람다를 사용해 호출하면 그 두 호출은 각각 따로 인라이닝된다.
// 인라인 함수의 본문 코드가 호출 지점에 복사되고 각 람다의 본문이 인라인 함수의 본문 코드에서 람다를 사용하는 위치에 복사된다.
// 참고로 함수에 더해 프로퍼티 접근자에도 inline을 명시할 수 있다.

