package chapter05.section2

import java.util.function.Supplier

// sam 생성자는 컴파일러가 생성한 함수로 람다를 단일 추상 메서드 인터페이스의 인스턴스로 명시적 변환해준다.
// 컴파일러가 변환을 자동으로 수행하지 못하는 맥락에서 사용할 수 있다.
// 예를 들어 함수형 인터페이스의 인스턴스를 반환하는 함수가 있다. 이 경우에는 람다를 SAM 생성자로 감싸야 한다.
fun createRunnable(): Runnable {
    // SAM 생성자의 이름은 사용하려는 함수형 인터페이스의 이름과 같다.
    // SAM 생성자느 하나의 인자만을 받아 함수형 인터페이스를 구현하는 클래스의 인스턴스를 반환한다.
    return Runnable { println("Hello, Kotlin!") }
}

// 이외에 람다로 생성한 함수형 인터페이스의 인스턴스를 변수에 저장해야 하는 경우에도 SAM 생성자를 사용할 수 있다.
val runnableInstance = Runnable {
    println("Hello, Kotlin!")
}

// SAM 생성자는 컴파일러에게 어떤 SAM 인터페이스르 람다로 구현하는 지 명시하는 역할을 하기도 한다.
// 예를 들어 메서드 오버로드로 인해 다른 타입의 SAM 인터페이스를 요구하는 메서드가 여러 개인 경우 SAM 생성자를 활용할 수 있다.
fun task(runnable: Runnable) {
    runnable.run()
}

fun task(supplier: Supplier<String>) {
    println(supplier.get())
}

fun samConstructorWithMethodOverload() {
    // task { println("Hello, Kotlin!") } // 컴파일 에러 발생
    task(Runnable { println("Hello, Kotlin!") })
    task(Supplier { "Hello, Kotlin!" })
}

/*
주의점으로 람다에는 익명 객체와 다르게 인스턴스 자신을 가리키는 this가 존재하지 않는다.
즉, 람다를 변환한 익명 클래스의 인스턴스 참조 방법이 없다.
람다식 내에서 this를 선언하면 람다를 둘러싼 클래스의 인스턴스를 가리킨다.
따라서 this를 활용해야 하는 경우에는 익명 객체를 사용하도록 하자.
 */

