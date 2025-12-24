package chapter3

// util 패키지에 명시된 joiner.kt의 최상위 함수를 import
import chapter3.util.joinToString

// 함수를 호출하기 쉽게 만들기
fun main() {
    val list = listOf(1, 2, 3)
    // joinToString 직접 구현 예시 (컬렉션에도 존재)
    // 함수 호출의 가독성이 좋지 않음
    println(joinToString(list, "; ", "(", ")"))
    // 코틀린에서는 함수에 전달할 인자를 명시적으로 지정 가능
    // 전달할 인자의 일부또는 전부에 대한 이름 명시 가능
    // 이 덕분에 인자 순서를 변경 가능
    println(joinToString(collection = list, separator = "; ", prefix = "(", postfix = ")"))

    // 디폴트 파라미터 활용 예시
    // 디폴트 파라미터가 명시되지 않은 파라미터를 제외하고는 모두 선택값이 됨
    println(joinToString(list))

    // 최상위 프로퍼티 사용 예시
    performOperation()
    reportOperationCount()
}

// 최상위 프로퍼티 선언
// 코드에서 상수를 정의할 때 사용할 수 있음
val UNIX_LINE_SEPARATOR = "\n"

// 만약 자바의 public static final 필드로 노출하고자 한다면 const를 추가하면 됨
const val UNIX_LINE_SEPARATOR_CONST = "\n"
// => public static final String UNIX_LINE_SEPARATOR_CONST = "\n"

// 최상위 프로퍼티 활용 예시
var opCount: Int = 0
fun performOperation() {
    opCount++
    // ...
}

fun reportOperationCount() = println("Operation count: $opCount")
