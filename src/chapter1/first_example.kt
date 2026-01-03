package chapter1

// 데이터 클래스
data class Person(
    // 읽기 전용 프로퍼티
    val name: String,
    val age: Int? = null // null이 될 수 있는 값과 파라미터 기본 값
)

// 최상위 함수
fun main() {
    val persons = listOf(
        Person("Alice", 29),
        Person("Bob")
    )

    val oldest = persons.maxBy { // 람다식
        it.age ?: 0 // null에 적용하는 엘비스 연산자
    }

    println("가장 나이가 많은 사람 $oldest")
}