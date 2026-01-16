package chapter8.section2

// 컬렉션에도 마찬가지로 null이 될 수 있는 변수를 저장할 수 있다.
// Int 또는 null을 저장하는 컬렉션을 반환하는 함수 예시
fun readNumbers(text: String): List<Int?> {
    // 변환이 가능하면 Int를, 아니면 null을 저장하는 리스트로 반환
    return text.lineSequence().map { it.toIntOrNull() }.toList()
}

// 마찬가지로 컬렉션 자체가 null이 될 수 있고, 두 가지 모두 합친 선언도 가능하다.
val nullList: List<Int>? = null
val nullVariableNullList: List<Int?>? = null

// null이 될 수 있는 값으로 이뤄진 컬렉션을 다룬다면 filterNotNull을 활용할 수 있다.
// 이 경우에는 컬렉션 내에 null이 없는 것이 보장되므로 List<Int> 타입이 된다.
fun filterNotNull(list: List<Int?>): List<Int> = list.filterNotNull()

// 코틀린에서는 컬렉션 내 데이터에 접근하는 인터페이스와 컬렉션 내 데이터를 변경하는 인터페이스를 분리하는 것이 자바와의 큰 차이점이다.
// kotlin.collections 패키지에는 Collection(읽기 전용)과 MutableCollection(변경 가능) 인터페이스가 존재한다.
// Collection에는 size, iterator(), contains() 같은 메서드가,
// MutableCollection는 Collection을 확장하여 add(), remove(), clear() 같은 변경 가능한 메서드가 존재한다.

// 이를 통해 코드를 읽을 때도 컬렉션 인터페이스에 따라서 해당 코드가 어떤 작업을 하는 지 쉽게 유추할 수 있다.
// 따라서 MutableCollection을 인자로 받는 함수의 경우에는 원본 변경 방지를 위해 복사해서 넘겨야 할 수도 있다.
// 가능하면 항상 읽기 전용 인터페이스를 우선적으로 고려하도록 하자.
fun mutableCollectionExample() {
    // 참고로 Collection이 MutableCollection의 상위 타입이므로 다음과 같은 다형성이 적용된다.
    val mutableList: MutableCollection<Int> = mutableListOf(1, 2, 3)
    // 이 경우 mutableList에서 값을 변경하면 list에서도 변경된 값을 읽을 수 있다.
    // 하지만 이렇게 구현된 경우에는 Collection을 사용하는 쪽에서는 컬렉션을 동시에 변경할 수 없다는 가정에 의존하게 되고,
    // 코드가 컬렉션을 사용하는 도중에 다른 스레드 등으로 컬렉션이 변경되는 상황으로 동시성 관련 예외나 오류가 발생할 수 있다.
    // 따라서 읽기 전용 컬렉션이 항상 스레드 안전 하지 않다는 것에 유의해야 한다.
    // 사용하는 쪽에서는 Collection일지라도, 내부적으로는 MutableCollection일 수도 있다는 것이다.
    val list: Collection<Int> = mutableList
}

// 코틀린의 컬렉션은 자바 컬렉션과 완벽히 대응되므로 아무 변환이 필요 없다.
// 단지 모든 자바 컬렉션 인터페이스마다 읽기 전용 인터페이스와 변경 가능한 인터페이스, 두 가지 인터페이스를 제공할 뿐이다.
/*
[Read-Only]                     [Mutable]
-------------------------------------------------------
 Iterable            <-------    MutableIterable
    ^                               ^
    |                               |
 Collection          <-------    MutableCollection
    ^       ^                       ^       ^
    |       |                       |       |
   List     |        <-------       |    MutableList
            |                       |       ^
           Set       <-------       |    MutableSet
                                    |       ^
                                    |       |-- HashSet (Set 구현체)
                                    |
                                    |-- ArrayList (List 구현체)

자바의 ArrayList와 HashSet은 코틀린의 변경 가능 인터페이스를 확장한다.
 */
// 위 구조도를 살펴보면 코틀린의 변경 가능 인터페이스는 java.util 패키지에 있는 인터페이스와 직접적으로 연관된다.
// 여기서 읽기 전용 인터페이스는 컬렉션을 변경할 수 있는 모든 요소를 제거한 상태이다.
// 따라서 두 인터페이스 모두 구현체로는 자바의 컬렉션 구현체가 활용된다.

// 이러한 두 개의 인터페이스 제공은 Map 클래스에서도 나타난다. 코틀린에서는 Map과 MutableMap 두 인터페이스를 제공한다.
// 다음 표는 여러 컬렉션을 만들 때 사용하는 함수를 나타낸다.
/*
| 컬렉션 타입 | 읽기 전용 타입 | 변경 가능 타입 |
| List | listOf, List | mutableListOf, MutableList, arrayListOf, buildList |
| Set | setOf | mutableSetOf, hashSetOf, linkedSetOf, sortedSetOf, buildSet |
| Map | mapOf | mutableMapOf, hashMapOf, linkedMapOf, sortedMapOf, buildMap |
 */

// 여기서 setOf와 mapOf는 읽기 전용이지만 내부적으로는 변경이 가능하다.
fun setOfAndMapOfExample() {
    val readOnlyMap: Map<String, Int> = mapOf("A" to 1, "B" to 2)
    val readOnlySet: Set<String> = setOf("A", "B")

    if (readOnlyMap is MutableMap) {
        readOnlyMap["C"] = 3 // 런타임에 에러가 안 날 수 있다
        println(readOnlyMap) // {A=1, B=2, C=3}
    }

    if (readOnlySet is MutableSet) {
        readOnlySet.add("C")
        println(readOnlySet) // [A, B, C]
    }
}

// 하지만 이들을 변경 가능한 컬렉션이라고 의존하면 안된다.
// 이후 코틀린 구현이 두 함수를 불변 컬렉션 인스턴스를 반환하게 만들 수도 있기 때문이다.


