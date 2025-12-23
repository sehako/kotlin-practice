package chapter2

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

}
