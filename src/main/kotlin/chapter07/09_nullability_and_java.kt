package chapter07

// 코틀린은 자바와의 상호 운용성이 뛰어난 언어지만, 자바는 null 가능성을 지원하지 않는다.
// 자바 코드에도 @Nullable과 @NotNull이 있다. 코틀린은 이 정보를 활용하여 각 어노테이션에 맞는 타입으로 간주한다.
// 또한 코틀린은 여러 null 가능성 어노테이션을 식별한다. (JSR-305 표준, 안드로이드, 젯브레인즈 도구 지원 어노테이션)

// 이런 null 가능성 어노테이션이 없는 경우에는 자바의 타입은 코틀린의 플랫폼 타입이 된다.
// 플랫폼 타입은 코틀린이 null 관련 정보를 알 수 없는 타입을 말한다.
// 이런 타입은 null이 될 수 있는 타입과 될 수 없는 타입, 두 타입 모두 가능하다.
// 사실상 자바 타입과 마찬가지로 모든 연산에 대한 책임이 개발자에게 주어진다.
// 따라서 컴파일러는 모든 연산을 허용하며, 아무 경고도 표시하지 않는다. (즉, NPE가 발생할 수 있다)

fun yellAt(person: JavaPerson) {
    // 두 선언 모두 올바른 선언이 된다.
    val s: String? = person.name
    val ss: String = person.name

    println(person.name.uppercase()) // name이 null이면 NPE가 발생한다.
}

// 따라서 getName()의 반환값을 null이 될 수 있는 타입으로 해석해서 null 안정성 연산을 활용하 수도 있다.
fun yellAtSafe(person: JavaPerson) {
    println((person.name ?: "Anyone").uppercase())
}

// 대부분의 자바 라이브러리는 null 관련 어노테이션을 사용하지 않기 때문에 타입을 다루기 쉽지만 NPE가 발생할 수 있다.
// 플랫폼 타입은 코틀린으로는 만들 수 없고, 자바 코드에서 가져온 타입만 될 수 있다. 하지만 IDE나 컴파일러 오류 메시지는 플랫폼 타입을 인식한다.
// val i: Int = person.name (ERROR: Type mismatch: inferred type is String! but Int was expected)
// 여기서 String! 타입은 코틀린 컴파일러와 IDE에서 자바 코드에서 온 플랫폼 타입을 명시하는 방법이다.

// 코틀린에서 자바 메서드를 오버라이드 할 때 그 메서드의 파라미터와 반환 타입을 null이 될 수 있는 타입으로 선언할지
// null이 될 수 없는 타입으로 선언할지 결정해야 한다.
// StringProcessor 인터페이스에 대해 코틀린 컴파일러는 다음과 같은 두 구현을 받아들인다.
class StringPrinter : StringProcessor {
    override fun process(value: String) {
        println(value)
    }
}

class NullableStringPrinter : StringProcessor {
    override fun process(value: String?) {
        value?.let { println(it) }
    }
}
// 자바 클래스나 인터페이스를 코틀린에서 상속 및 구현할 경우 null 가능성을 제대로 처리하는 일이 중요하다.
// 구현 메서드를 다른 코틀린 코드가 호출할 수 있으므로
// 코틀린 컴파일러는 null이 될 수 없는 타입으로 선언한 모든 파라미터에 대해 null이 아님을 검사하는 단언문을 만들어준다.
// 자바 코드가 그 메서드에게 null 값을 넘기면 해당 단언문이 발동되어 예외가 발생한다.
// 심지어 파라미터를 메서드에서 사용하지 않아도 이런 예외는 불가피하다.
