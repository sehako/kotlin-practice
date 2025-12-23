package chapter2

fun main() {
    // 문자열 템플릿: 문자열 내에서의 변수 사용
    val input = readln()
    val name = if (input.isEmpty()) "Kotlin" else input
    // $변수명으로 문자열 내에서 변수 활용
    // 자바의 문자열 접합 연산과 동일한 기능 ("Hello, " + name + "!")
    // 존재하지 않는 변수를 문자열 탬플릿에 사용하면 컴파일 오류 발생
    println("Hello, $name!")

    // 만약 $를 문자로 넣고자 한다면 \를 활용
    println("12\$ dollar!")

    // 문자열 템플릿은 변수뿐 아니라 ${}를 통해 내부에서 복잡한 식을 활용 가능
    val wonDollar = 1470;
    println("${12 * wonDollar} won!");

    // 이를 활용한 이름 입출력 코드 예시
    println("Hello, ${if (name.isEmpty()) "Kotlin" else name}!")
}