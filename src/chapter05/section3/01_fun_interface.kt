package chapter05.section3

// 코틀린에서 함수형 인터페이스를 정의할 때에는 interface 키워드 앞에 fun을 명시하면 된다.
// 자바처럼 여러 디폴트 메서드가 존재할 수 있으며 단 하나의 추상 메서드만 있으면 된다.
fun interface IntCondition {
    fun check(i: Int): Boolean
    fun checkString(s: String): Boolean = check(s.toInt())
    fun checkChar(c: Char): Boolean = check(c.digitToInt())
}

// 이를 SAM 변환을 사용해 이 인터페이스를 구현하는 인스턴스를 만들 수 있다.
fun intConditionWithSAM() {
    // SAM 변환
    val isOdd = IntCondition { it % 2 != 0 }
    println(isOdd.check(3))
    println(isOdd.checkString("3"))
}

// 또한 어떤 함수의 파라미터로 받는 경우에는 람다 구현이나 람다에 대한 참조를 직접 넘길 수 있다.
fun checkCondition(i: Int, condition: IntCondition): Boolean {
    return condition.check(i)
}

fun usingDynamicCondition() {
    // 람다 활용 예시
    checkCondition(3) { it % 2 != 0 }

    val isOdd: (Int) -> Boolean = { it % 2 != 0 }
    checkCondition(3, isOdd)
}

/*
fun interface는 자바 코드를 깔끔하게 유지할 수 있는 수단이기도 하다.
코틀린 함수 타입은 파라미터와 반환 타입을 타입 파라미터로 한느 제네틱 타입인 객체로 번역된다.
아무것도 반환하지 않는 함수의 경우 코틀린은 Unit을 자바 void에 해당하는 역할로 사용한다.
따라서 자바에서 호출할 때 명시적으로 Unit.INSTANCE를 반환할 필요가 있따.
이런 문제를 fun interface를 사용하면 피할 수 있으며 코드도 더 간결해진다. (consumer.kt 참고)
 */

