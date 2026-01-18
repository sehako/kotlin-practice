package chapter07

// null이 될 수 있는 타입에 대한 확장 함수를 정의하면 null을 다루는 강력한 도구로 활용할 수 있다.
// 어떤 메서드를 호출하기 전에 수신 객체 역할을 하는 변수가 null이 될 수 없다고 보장하는 대신,
// 메서드 호출이 null을 수신 객체로 받고 내부에서 null을 처리하게 할 수 있다.
// 일반 멤버 호출은 객체 인스턴스를 통해 디스패치되므로 null 여부를 검사하지 않는다.

// 실제로 String 타입에는 확장 함수로 null을 손쉽게 검사할 수 있다.
fun verifyUserInput(input: String?) {
    // String? 타입의 수신 객체에 대해 안전한 호출 없이 호출할 수 있는 isEmptyOrNull()이나 isBlankOrNull() 메서드가 있다.
    if (input.isNullOrBlank()) {
        println("input is blank")
    }
}

// 내부 구현은 다음과 같다.
/*
@kotlin.internal.InlineOnly
public inline fun CharSequence?.isNullOrBlank(): Boolean {
    ...
    return this == null || this.isBlank()
}
 */
// null이 될 수 있는 타입에 대한 확장을 정의하면 null이 될 수 있는 값에 대해 그 확장 함수를 호출할 수 있다.
// 자바에서는 this는 그 메서드가 호출된 수신 객체를 가리키므로 항상 null이 아니지만 코틀린에서는 this가 null이 될 수 있다.

// 마지막으로 위와 같은 null 여부 검사 메서드를 통과해도 null이 될 수 없는 값이 되는 것은 아니다.
fun verifyUserInputNullable(input: String?) {
    if (input.isNullOrBlank()) {
        println("input is blank")
    }

    input?.uppercase() // 안전한 호출을 명시해야 한다.
//    input.uppercase() <- 컴파일 에러
}
