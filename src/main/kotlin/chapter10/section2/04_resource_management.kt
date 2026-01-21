package chapter10.section2

import java.io.BufferedReader
import java.io.FileReader
import java.util.concurrent.locks.Lock
import kotlin.concurrent.withLock
import kotlin.io.path.Path
import kotlin.io.path.useLines

// 람다로 중복을 없앨 수 있는 일반적인 패턴 중 하나로 자원 관리가 있다.
// 자원은 파일, 락, 데이터베이스 트랜잭션 등 다른 여러 대상을 가리킬 수 있다.
// 코틀린은 앞선 synchronized 함수와 유사한 기능을 withLock으로 제공한다.

fun withLockExample(lock: Lock) {
    lock.withLock {
        // 락에 의해 보호되는 자원 사용
    }

    // 이는 inline 함수로 정의되어 있다.
    /*
    @kotlin.internal.InlineOnly
    public inline fun <T> Lock.withLock(action: () -> T): T {
        // ...
    }
     */
}

// 파일의 경우에도 use 함수를 통해 Closable를 구현한 객체에 대해 호출하는 확장 함수를 제공한다.
fun readFirstLineFromFile(fileName: String): String {
    // 이 역시 인라인 함수다.
    BufferedReader(FileReader(fileName)).use {
        // 비로컬 return (람다가 아닌 람다 호출이 본문에 포함된 함수를 끝내면서 값을 반환)
        return it.readLine()
    }
}

// File과 Path에 대해 정의된 useLines라는 확장 함수도 있다.
fun readFirstLineFromFilePath(fileName: String): String =
    Path(fileName).useLines { return it.first() }

// 코틀린에서는 try-with-resources를 사용하지 말고 이러한 함수들을 사용해서 자원을 관리하도록 하자.


