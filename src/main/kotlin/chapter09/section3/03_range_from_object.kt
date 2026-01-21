package chapter09.section3

import java.time.LocalDate

// 범위는 .. 구문으로 만들 수 있다. 이는 rangeTo 함수 호출을 간략하게 표현하는 방법이다.
// rangeTo 함수는 범위를 반환한다. 클래스에도 이를 정의할 수 있으며,
// 만약 Comparable을 구현한 경우에는 정의 없이도 범위 연산을 사용할 수 있다.
// 그 이유는 코틀린 표준 라이브러리는 모든 Comparable 객체에 대해 적용 가능한 rangeTo 함수가 있기 때문이다.
fun dateRange() {
    val now = LocalDate.now()
    // 오늘부터 10일 짜리 범위를 생성한다.
    val vacation = now..now.plusDays(10)
    // 이 식은 컴파일러에 의해 now.rangeTo(now.plusDays(10))으로 변환된다.
    println(now.plusWeeks(1) in vacation) // true
}

// 범위 연산자는 우선순위가 낮기도하고, 가독성 측면에서도 여러 연산자와 사용할 경우 괄호로 감싸주는 것이 일반적이다.
fun writeRangeExample() {
    val n = 9
    println(0..(n + 1))

    // 다른 연산자를 사용할 경우에도 다음과 같이 괄호로 둘러싸야 한다.
    (0..n).forEach { println(it) }
}

// 끝 숫자에 대한 열린 구간은 rangeUntil 연산자가 사용된다.
// ..< 또는 until로 호출할 수 있다.
fun rangeUntilExample() {
    val n = 9
    println(0 until n + 1)
    println(0..<n + 1)
}
