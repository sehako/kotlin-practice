package chapter9.section3

import java.time.LocalDate

// 코틀린의 for 반복은 범위 검사와 같은 in 연산자를 사용하지만 의미는 달라진다.
// 이 경우에는 내부적으로 이터레이터를 얻어서 hasNext와 next 호출을 반복하는 식으로 변환된다.
// 코틀린에서는 iterator 메서드를 확장 함수로 정의할 수 있다.
// 이 덕분에 CharSequence에 대한 iterator 확장 함수를 정의하여 문자열에 대한 for 반복이 가능한 것이다.
fun stringIterator() {
    for (char in "Hello") {
    }
}

// 마찬가지로 iterator 메서드를 직접 구현할 수도 있다.
// 다음은 LocalDate 객체에 대한 iterator 확장 함수 정의 예시이다.
// rangeTo 라이브러리 함수는 ClosedRange의 인스턴스를 반환한다.
// 따라서 ClosedRange<LocalDate>에 대한 확장 함수 iterator를 정의하여 LocalDate의 범위 객체를 for 반복에 사용할 수 있다.
operator fun ClosedRange<LocalDate>.iterator(): Iterator<LocalDate> =
    object : Iterator<LocalDate> {
        private var current = start

        // compareTo 관례를 이용한 날짜 비교
        override fun hasNext() = current <= endInclusive
        override fun next(): LocalDate {
            val result = current
            // 현재 날짜를 1일 뒤로 변경
            current = current.plusDays(1)
            return result
        }
    }

fun dateIterator() {
    val newYear = LocalDate.ofYearDay(2042, 1)
    val daysOff = newYear..newYear.plusDays(365)
    for (dayOff in daysOff) {
        println(dayOff)
    }
}

