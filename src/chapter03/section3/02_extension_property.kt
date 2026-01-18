package chapter03.section3

// 프로퍼티 형식의 구문으로 사용할 수 있는 API를 추가할 수 있음
// 프로퍼티이지만 실제로 확장 프로퍼티는 아무 상태도 가질 수 없다.
// 따라서 확장 프로퍼티는 항상 커스텀 접근자르 정의함

fun main() {
    // 확장 프로퍼티 선언 예시
    println("Kotlin".lastChar)

    // 변경 가능한 확장 프로퍼티 선언 예시
    val sb = StringBuilder("Kotlin?")
    // 확장 프로퍼티의 사용은 멤버 변수 사용 방법과 같다.
    sb.lastChar = '!'
    println(sb.lastChar)
}

// 문자열에 대한 확장 프로퍼티 선언 예시
val String.lastChar: Char
    // 최소한 getter는 꼭 정의해야 함
    get() = this[length - 1]

// 변경 가능한 확장 프로퍼티 선언
// 자바에서 확장 프로퍼티를 사용하고자 한다면 다음과 같음
// 파일이름Kt.getLastChar("Java?") 또는 파일이름Kt.getLastChar(sb, '!')
var StringBuilder.lastChar: Char
    get() = this[length - 1]
    set(value) {
        this[length - 1] = value
    }
