package chapter11.section2

import java.util.*

// 자바와 마찬가지로 코틀린의 제네릭 타입 인자 정보는 런타임 상황에서 모두 지워진다.
// 즉, 제네릭 클래스 인스턴스가 해당 인스턴스를 생성할 때 쓰인 타입 인자에 대한 정보를 유지하지 않는다.
// 예를 들어 List<String>으로 객체를 만들어도 런타임에서는 List로만 보이고 어떤 타입의 원소를 저장하는지는 알 수 없다.
// 이렇게 타입 인자가 소거되기 때문에 실행 시점에 타입 인자를 검사할 수 없다.
// 사용자 입력에 따라서 List<String>이나 List<Int>를 반환하는 함수가 있다고 가정해보자.
fun readNumbersOrWords(): List<Any> {
    val input = readln()
    val words: List<String> = input.split(",")
    val numbers: List<Int> = words.mapNotNull { it.toIntOrNull() }
    return numbers.ifEmpty { words }
}

fun printList(l: List<Any>) = when (l) {
    // Cannot check for instance of erased type 컴파일 오류 발생
//    is List<Int> -> println(l.joinToString())
//    is List<String> -> println(l.joinToString())
    else -> {}
}

// List인 것을 알 수는 있지만 실행 시점에 어떤 값이 들어있는 리스트인지는 알 수 없다. 그런 정보는 지워지기 때문이다.
// 주어진 값이 List라는 것을 확인하고자 한다면 스타 프로젝션(*)구문을 사용하면 된다.
// as나 as? 캐스팅에도 여전히 제네릭 타입을 사용할 수 있다.
// 하지만 이 경우에도 여전히 타입 인자가 다른 타입으로 캐스팅해도 여전히 캐스팅에 성공한다는 점에 유의해야 한다.
fun printSum(c: Collection<*>) {
    // 컴파일러는 단순히 경고만 하고 컴파일을 진행한다.
    // 따라서 List<String>을 전달해도 이는 런타임 시점에는 여전히 List이므로 intList로 대입되어 sum을 호출하게 된다.
    val intList = c as? List<Int> ?: throw IllegalArgumentException("Argument must be a list of Ints")
    println(intList.sum()) // 이 시점에서 ClassCastException이 발생한다.
}

// 참고로 코틀린 컴파일러는 컴파일 시점에 타입 정보가 주어진 경우에는 is 검사를 수행할 수 있다.
fun printSumIntCollection(c: Collection<Int>) = when (c) {
    is List<Int> -> println(c.sum())
    is Set<Int> -> println(c.sum())
    else -> throw IllegalArgumentException("Argument must be a collection of Ints")
}

// 코틀린 제네릭 타입의 인자 정보는 실행 시점에 지워지므로
// 제네릭 클래스의 인스턴스가 있어도 해당 인스턴스를 만들 때 사용한 타입 인자를 알 수 없다.
// 이는 제네릭 함수의 타입 인자도 마찬가지다.
//fun <T> isA(value: Any) = value is T <- 컴파일 오류 발생

// 하지만 인라인 함수의 타입 파라미터는 실체화된다. 따라서 런타임 시점에 인라인 함수의 타입 인자를 알 수 있다.
// 인라인 함수와 타입 파라미터 앞에 reified를 명시하면 된다.
inline fun <reified T> isA(value: Any) = value is T

// 실체화된 타입 파라미터를 활용하는 간단한 예제 중 하나는 표준 라이브러리 filterIsInstance이다.
fun filterIsInstanceExample() {
    val items = listOf("one", 2, "three")
    // String 타입의 원소만 선택
    val strings: List<String> = items.filterIsInstance<String>()
}

// 내부 구현은 다음과 같다.
/*
public inline fun <reified R, C : MutableCollection<in R>> Iterable<*>.filterIsInstanceTo(destination: C): C {
    for (element in this) if (element is R) destination.add(element)
    return destination
}
 */

// 자바 코드에서는 reified 타입 파라미터를 사용하는 인라인 함수를 호출할 수 없다는 것에 유의해야 한다.
// 자바에서는 코틀린 인라인 함수를 다른 함수처럼 호출한다. 그런 경우 인라인 함수를 호출해도 실제로 인라이닝 되지는 않는다.
// 인라인 함수에서는 실체화된 타입 파라미터가 여럿 있거나 실체화된 타입 파라미터와 실체화되지 않은 타입 파라미터가 함께 있을 수도 있다.
// 람다를 파라미터로 받지 않지만 filterIsInstance를 인라인 함수로 정의했다. 즉, 인라인 함수는 다음 두 가지 상황에서 사용한다.
/*
- 성능을 고려하여 람다를 함수 본문에 인라이닝 해야 하는 경우
- 실체화된 타입 파라미터를 사용하기 위한 경우
 */

// 클래스 참조를 실체화된 타입 파라미터로 대신함으로써 java.lang.Class 타입 인자를
// 파라미터로 받는 API에 대한 코틀린 어댑터를 구축하는 경우 실체화된 타입 파라미터를 자주 사용한다. 대표적인 예로 JDK의 ServiceLoader가 있다.
// 이는 어떤 추상 클래스나 인터페이스를 표현하는 java.lang.Class를 받아 그 클래스나 인스턴스를 구현한 인스턴스를 반환한다.
// 표준 자바 API인 ServiceLoader를 사용해 서비스를 읽어 들이려면 다음 코드처럼 호출해야 한다.
val serviceImpl = ServiceLoader.load(Int::class.java) // 임의로 HashMap 객체를 생성하도록 하였다.
// ::class.java 구문은 코틀린 클래스에 대응하는 java.lang.Class 참조를 얻는 방법을 보여준다. 이는 Service.class라는 자바 코드와 완전히 같다.

// 이 예제를 실체화된 타입 파라미터를 사용하면 다음과 같이 간결하게 표현할 수 있다.
inline fun <reified T> loadService(): ServiceLoader<T>? {
    return ServiceLoader.load(T::class.java) // 타입 파라미터의 클래스를 가져온다.
}

// 이를 다음과 같이 사용할 수 있다.
val serviceImpl2 = loadService<Int>()

// 인라인과 실체화된 타입 파라미터를 사용할 수 있는 코틀린 구성 요소가 함수만 있는 것은 아니다.
// 제네릭 타입에 대해 프로퍼티 접근자를 정의하는 경우 프로퍼티를 inline으로 표시하고
// 타입 파라미터를 reified로 하면 타입 인자에 쓰인 구체적인 클래스를 참조할 수 있다.
inline val <reified T> T.canonical: String
    get() = T::class.java.canonicalName

fun printCanonicalName() {
    println(1.canonical) // java.lang.Integer
}
