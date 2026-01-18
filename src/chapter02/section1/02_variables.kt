package chapter02.section1

fun main() {
    // 코틀린에서는 변수 선언 키워드로 val과 var를 활용
    // 변수 선언 시 리터럴 값을 기반으로 타입 추론 수행
    val str: String = "Hello, Kotlin!"
    val strWithoutType = "Hello, Kotlin!"

    // 정수의 경우 리터럴에 따라서 다양한 타입으로 추론됨
    val int: Int = 1
    val long: Long = 1L
    val double: Double = 1.0e6 // 1 x 10^6


    // 변수 선언과 동시에 초기화가 이루어지지 않는 경우에는 타입을 명시해야 함
    val num: Int
    num = 1

    // val vs var
    // val: 읽기 전용 참조로, 한 번만 초기화 가능하며 이후에는 다른 값 대입 불가능
    // var: 재대입 가능한 참조를 선언, 다른 값 대입 가능
    // 기본적으로 모든 변수를 val로 선언하고, 필요한 경우에만 var로 변경
    // 한 번만 초기화 할 수 있다는 특성을 활용한 조건부 초기화 로직 예시
    val output: String
    if (num == 1) {
        output = "One"
    } else {
        output = "More than one"
    }

    // val의 참조가 가리키는 객체의 내부 값은 변경 가능 (ex. 가변 리스트에 원소 삽입)
    val list = mutableListOf("Kotlin", "Java")
    list.add("Scala")

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
