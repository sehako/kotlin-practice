package part01.chapter02.section4

// 코틀린에서의 in은 범위를 검사하는데도 사용할 수 있음
fun main() {
    println(isLetter('a'))
    println(isNotDigit('a'))
    println(recognize('a'))

    // in의 비교 대상은 문자나 숫자 뿐 아닌 Comparable을 구현한 클래스라면 모두 가능
    println("Kotlin" in "Java".."Scala") // 이 경우 각 문자열의 알파벳을 순서대로 비교
    println("Kotlin" in setOf("Java", "Scala")) // 이 경우에는 컬렉션의 요소에 Kotlin이 있는 지 검사
}

// 주어진 변수 c가 a <= c <= z 이거나 A <= c <= Z 인지 검사
fun isLetter(c: Char) = c in 'a'..'z' || c in 'A'..'Z'

// 주어진 변수 c가 0 <= c <= 9가 아닌지 검사
fun isNotDigit(c: Char) = c !in '0'..'9'

// 이런 용법은 when에서도 사용이 가능함
fun recognize(c: Char) = when (c) {
    in '0'..'9' -> "Digit"
    in 'a'..'z', in 'A'..'Z' -> "Letter"
    else -> "Other"
}
