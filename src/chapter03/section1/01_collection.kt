package chapter03.section1

import chapter03.section4.to

fun main() {
    // Set, List, Map 예시
    val set = setOf(1, 2, 3, 4, 5)
    val list = listOf(1, 2, 3, 4, 5)
    val map = mapOf(1 to "one", 2 to "two", 3 to "three")

    // 코틀린은 표준 자바 컬렉션 클래스를 사용
    // 하지만 자바와 다르게 코틀린 컬렉션 인터페이스는 기본적으로 읽기 전용이다.
    println(set.javaClass) // LinkedHashSet
    println(list.javaClass) // ArrayList
    println(map.javaClass) // LinkedHashMap

    // 자바와 다르게 더 많은 기능을 제공함
    val strings = listOf("first", "second", "third")
    strings.last() // third
    println(strings.shuffled())

    val numbers = setOf(1, 14, 2)
    println(numbers.sum())
}

