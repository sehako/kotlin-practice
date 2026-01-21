package chapter08.section1

// Any
// 자바에 Object처럼 코틀린은 Any 타입이 클래스의 최상위 타입이다.
// 자바와 다른 점은 자바는 원시 타입은 Object의 하위 타입이 아니지만, 코틀린은 Int가 Any의 하위 타입이 된다.
// 코틀린에서도 원시 타입 값을 Any 타입의 변수에 대입하면 자동으로 값을 객체로 감싼다.
val answer: Any = 10 // 10이 박싱된다.

// 또한 자바의 Object 타입이 코틀린에 Any 타입이 되므로 자바 플랫폼 타입인 Any!로 취급되며, 그 반대로는 Object로 컴파일된다.
// toString, equals, hashCode는 Any에 정의된 메서드를 상속한 것이다.
// 하지만 Object에 있는 wait나 notify는 Any에서 사용할 수 없으므로 이 경우에는 자바 Object로 캐스트해야 한다.

// Unit 타입은 자바의 void와 같은 기능을 한다. 즉 다음 두 함수는 같다고 볼 수 있다.
fun functionUnit(): Unit {}
fun functionVoid() {}

// Unit
// 대부분의 경우 차이가 없으며, 코틀린 함수의 반환 타입이 Unit이고 그 함수가 제네릭 함수를 오버라이드 하지 않으면
// 그 함수는 내부에서 자바 void로 컴파일된다. 따라서 자바에서 코틀린 함수를 오버라이드 할 경우에도 void가 반환 타입이 된다.
// 해당 타입이 유의미한 경우는 void와는 다르게 타입 인자로 사용할 수 있다는 것이다.
// 따라서 제네릭 파라미터를 반환하는 함수를 오버라이드 하면서 반환값이 없음을 표현할 때 유용하다.
// 자바의 Void와 유사하지만, 자바와 달리 'return null' 같은 코드를 명시하지 않아도 된다는 장점이 있다.
interface ProcessorUnit<T> {
    fun process(): T
}

class ProcessorVoid : ProcessorUnit<Unit> {
    override fun process() {
        // 컴파일러가 암시적으로 return Unit을 명시한다.
    }
}

// Nothing
// 코틀린에서는 성공적으로 값을 돌려주는 일이 없다는 의미로 Nothing 타입을 제공한다.
// 아무런 값을 저장할 수 없기 때문에 아무런 의미도 없다.
fun fail(message: String): Nothing = throw IllegalStateException(message)

fun nothingExample() {
    fail("Error occurred!")
}

// 이를 반환하는 함수를 엘비스 연산자의 오른쪽에 사용해서 전제조건을 검사할 수 있다.
fun showAddress(address: String?) {
    address ?: fail("No address")
    println(address)
}
