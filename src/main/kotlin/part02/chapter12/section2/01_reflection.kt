package part02.chapter12.section2

import kotlin.reflect.KFunction
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

// 리플렉션은 실행 시점에 객체의 프로퍼티와 메서드에 접근할 수 있게 해주는 방법이다.
// 보통 객체의 메서드나 프로퍼티에 접근할 때는 프로그램 소스코드에 구체적인 선언이 있는 메서드나 프로퍼티 이름을 사용하며,
// 컴파일러는 그런 이름이 실제로 가리키는 선언을 정적으로 찾아내 해당하는 선언이 실제 존재함을 보장한다.

// 하지만 타입과 관계없이 객체를 다뤄야 하거나 객체가 제공하는 메서드나 프로퍼티 이름을 오칙 실행 시점에만 알 수 있는 경우가 있다.
// 제이키드의 직렬화 라이브러리는 어떤 객체든 JSON으로 변환할 수 있어야 하기 때문에 특정 클래스나 프로프티만 참조할 수 없다.
// 이런 경우 리플렉션을 사용해야 한다. 코틀린에서 리플렉션을 사용하려면 보통은 코틀린 리플렉션 API를 다루면 된다.
// 이는 kotlin.reflect, kotlin.reflect.full(kotlin-reflect 라이브러리 필요) 패키지에 정의되어 있다.
// 코틀린 리플렉션 API에 대한 차선책으로 java.lang.reflect 패키지에 정의된 자바 표준 리플렉션이 있다.
// 코틀린 클래스는 일반 자바 바이트코드로 컴파일되므로 자바 리플렉션 API도 코틀린 클래스를 컴파일한 바이트코드를 잘 지원한다.
// 즉 리플렉션을 사용하는 자바 라이브러리와 코틀린 코드가 완전히 호환된다.

// 코틀린 리플렉션 API에 대해서 알아보도록 하자.
// KClass를 사용하면 클래스 내에 있는 모든 선언을 열거하고 각 선언에 접근하거나 클래스의 상위 클래스를 얻는 작업이 가능하다.
class KClassPerson(val name: String, val age: Int)

fun useKClass() {
    val person = KClassPerson("Alice", 29)
    val kClass = person::class // KClass<out KClassPerson>을 반환한다.
    println(kClass.simpleName) // KClassPerson
    // 클래스와 모든 조상 클래스 내부에 정의된 비확장 프로퍼티를 모두 가져온다.
    kClass.memberProperties.forEach { println(it.name) }
    // name
    // age
    // KClass 선언을 살펴보면 클래스 내부를 살펴볼 수 있는 다양하고 유용한 메서드를 볼 수 있다.
    // https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.reflect/-k-class/
    // kClass에 대한 전체 메서드는 위 문서를 참고하도록 하자.
}

// 클래스의 모든 멤버의 컬렉션인 members는 KCallable 인스턴스의 컬렉션이다.
// 이는 함수와 프로퍼티를 이루는 공통 상위 인터페이스로, 내부적으로 call 메서드가 존재한다.
// 이를 사용하면 함수나 프로퍼티의 getter를 호출할 수 있다.
fun kCallableExample(x: Int) = println(x)
fun callFunctionWithCallable() {
    // 참조로 이루어지는 식의 값 타입이 리플렉션 API에 있는 kFunction 클래스의 인스턴스임을 알 수 있다.
    // 예전에는 KFunctionN 인터페이스가 있었지만 이제는 KFunction<out R>을 명시하면
    // 컴파일러가 내부적으로 FunctionN 및 KFunctionN을 합성하여 처리한다.
    val kFunction: KFunction<Unit> = ::kCallableExample
    kFunction.call(10) // 원래 함수에 정의된 파라미터 개수와 타입이 맞아야 한다.

    // call은 vararg로 여러 인자를 받기 때문에 예외가 발생할 수 있다.
    // 따라서 호출 시 인자의 개수와 타입 안정성을 가지고 싶다면 Function을 활용하도록 하자.
    val function: Function1<Int, Unit> = ::kCallableExample
    function.invoke(10) // 타입과 인자 개수에 대해서 안정성을 확보할 수 있음
}

// KProperty를 살펴보자. 이는 프로퍼티의 getter를 호출한다.
// 하지만 프로퍼티 인터페이스는 더 좋은 방법으로 get 메서드를 제공한다.
var counter = 0
fun counterKProperty() {
    // 최상위 읽기 전용 프로퍼티는 KProperty0
    // 가변 프로퍼티는 KMutableProperty0 인터페이스의 인스턴스로 표현된다.
    // 둘 모두 인자가 없는 get 메서드를 제공한다.
    val kProperty: KMutableProperty0<Int> = ::counter
    kProperty.set(10)
    println(kProperty.get())
}

// 멤버 프로퍼티는 KProperty1이나 KMutableProperty1 인스턴스로 표현된다.
// 여기에는 인자가 1개인 get 메서드가 있으며, 이를 통해 프로퍼티의 값을 얻으려면 값을 얻을 수신 객체 인스턴스를 넘겨야 한다.
class KPropertyPerson(val name: String, val age: Int)

fun kProperty1Example() {
    val person = KPropertyPerson("Alice", 29)
    // 수신 객체 타입과 프로퍼티 타입을 전달받는 제네릭 클래스
    val kProperty: KProperty1<KPropertyPerson, Int> = KPropertyPerson::age
    println(kProperty.get(person))
}

// 최상위 수준이나 클래스 안에 정의된 프로퍼티만 리플렉션으로 접근할 수 있고, 함수의 로컬 변수에는 접근할 수 없다.

// 모든 선언에 어노테이션이 붙을 수 있기 때문에 KClass, KFunction, KParameter 등 실행 시점에 선언을 표현하는 인터페이스들은
// 모든 KAnnotatedElement를 확장한다.
// 정리하자면 KClass는 클래스와 객체를, KProperty는 모든 프로퍼티를, 변경 가능한 프로퍼티에 대해서는 KMutableProperty로 표현한다.
// 그리고 이들에 선언된 get과 set으로 프로퍼티 접근자를 함수처럼 다룰 수 있으며, 이들은 모두 KFunction을 확장한다.


