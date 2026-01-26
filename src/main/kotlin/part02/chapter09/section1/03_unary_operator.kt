package part02.chapter09.section1

// 코틀린은 단항 연산자 또한 지원한다.
data class UnaryPoint(val x: Int, val y: Int) {
    // 단항 연산자를 오버로딩하기 위해 사용하는 함수는 인자를 취하지 않는다.
    operator fun unaryMinus() = UnaryPoint(-x, -y)
}

fun convertToMinus() {
    val point = UnaryPoint(1, 2)
    println(-point) // UnaryPoint(x=-1, y=-2)
}

/*
단항 연산자 목록은 다음과 같다.
+(unaryPlus), !(not), ++(inc), --(dec)
inc나 dec 함수를 정의해 증가 / 감소 연산자를 오버로딩하는 경우
컴파일러는 일반적인 값에 대한 전위와 후위 증가 / 감소 연산자와 같은 의미를 제공한다.
 */
