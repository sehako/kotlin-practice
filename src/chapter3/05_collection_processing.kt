package chapter3

// 컬렉션을 처리할 때 쓰는 코틀린 표준 라이브러리 함수 활용 예시
// vararg: 호출 시 인자 개수가 달라질 수 있는 함수 정의
// 중위 함수 호출 구문을 사용하면 인자가 하나뿐인 메서드를 간편하게 호출 가능
// 구조 분해 선언을 활용하면 복합적인 값을 분해해서 여러 변수에 나눠 담을 수 있음

fun main() {
    // 참고로 코틀린이 제공하는 컬렉션 편의 메서드도 확장 함수로 설계된 것
    // 확장 함수로 설계되고 항상 디폴트로 import된 것
    val list = listOf(1, 2, 3)
    list.last()
    list.sum()

    // 가변 인자 함수
    // 자바의 ..같은 역할이 바로 vararg
    val customList = customListOf(1, 2, 3, 4)

    // 스프레드 연산자
    // 배열에 있는 원소를 가변 길이 인자로 넘길 때 사용
    val arr = arrayOf(1, 2, 3)
    println(customListOf(1, *arr))

    // 중위 호출
    val map = mapOf("one" to 1, "two" to 2) // 여기서 to는 코틀린 키워드가 아닌 중위 호출로 to()를 호출한 것
    // 따라서 다음 두 호출은 동일
    // 1.to("one") == 1 to "one"
    // 인자가 하나뿐인 일반 메서드나 인자가 하나뿐인 확장 함수에만 중위 호출 사용 가능

    // to()는 일반 메서드이므로 다음과 같은 용법도 가능함
    val (number, name) = 1 to "one" // 이를 구조 분해 선언 이라고 함
}

// listOf 내부 구현 예시
fun <T> customListOf(vararg elements: T): List<T> {
    return elements.asList()
}

// 중위 호출 허용 함수(메서드) 작성 예시
// infix로 중위 호출을 허용할 수 있음
// Pair는 코틀린 표준 라이브러리 클래스로, 이름 그대로 두 원소로 이루어진 순서쌍을 표현
infix fun Any.to(other: Any) = Pair(this, other)
