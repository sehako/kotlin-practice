package chapter09.section3

// 코틀린에서 Map에 접근할 때는 각괄호([])를 사용한다.
fun mapAccess() {
    val map = mutableMapOf<Int, String>()
    map[1] = "one" // 1 to "one" -> map.put(1, "one)
    println(map[1]) // "one" -> map.get(1)
}

// 이런 관례를 클래스에도 적용할 수 있다.
data class AccessPoint(val x: Int, val y: Int)

operator fun AccessPoint.get(index: Int): Int = when (index) {
    // 주어진 인덱스에 해당하는 좌표 설정
    0 -> x
    1 -> y
    // 그 이외에는 예외 처리
    else -> throw IndexOutOfBoundsException("Invalid coordinate $index")
}
// 이러한 메서드는 파라미터로 Int가 아닐 수도 있으며, 여러 파라미터를 사용하는 get을 정의할 수도 있다.
// 또한 여러 get 메서드를 오버로딩 할 수 있다.

// 또한 인덱스에 해당하는 컬렉션 원소를 각괄호를 사용해 쓰는 함수를 정의할 수도 있다.
// 물론 이 경우에는 값이 var이어야 한다는 것에 유의해야 한다.
data class MutableAccessPoint(var x: Int, var y: Int)

operator fun MutableAccessPoint.set(index: Int, value: Int) = when (index) {
    0 -> x = value
    1 -> y = value
    else -> throw IndexOutOfBoundsException("Invalid coordinate $index")
}

