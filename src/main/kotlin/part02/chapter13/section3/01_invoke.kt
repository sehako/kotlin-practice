package part02.chapter13.section3

// invoke 관례를 사용하면 어떤 커스텀 타입의 객체를 함수처럼 호출할 수 있다.
// 이를 사용하면 함수와 똑같은 구문을 지원하는 객체를 정의할 수 있다. 이는 DSL에서는 아주 유용한 관례가 된다.

// invoke의 동작을 보여주는 예시를 보도록 하자.
class Greeter(val greeting: String) {
    operator fun invoke(name: String) = "$greeting $name!"
}

fun greetingExample() {
    val greeter = Greeter("Hello")
    // invoke가 호출됨
    greeter("Kotlin") // Hello Kotlin!
}

// invoke는 괄호를 통해 호출되는 관례이다. 이를 통해 인스턴스를 함수처럼 호출할 수 있다.
// 이 메서드 시그니처에 대한 요구 사항은 없다. 따라서 원하는 대로 파라미터 개수나 타입을 지정할 수 있다.
// 심지어 invoke를 오버로딩하여 여러 파라미터 타입을 지원할 수도 있다.

// 일반적인 람다 호출이 사실은 이러한 invoke 관례를 적용한 것이다.
// 인라인 람다를 제외하면 모든 람다는 함수형 인터페이스를 구현하는 클래스로 컴파일되기 때문이다.
