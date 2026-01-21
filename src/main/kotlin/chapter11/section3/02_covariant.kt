package chapter11.section3

// 공변적인 클래스는 제네릭 클래스에 대해 A가 B의 하위 타입일 때 제네릭의 타입 파라미터로 전달되어도 여전히 하위 타입인 경우를 말한다.
// 코틀린에서 제네릭 클래스가 타입 파라미터에 대해 공변적이라는 것을 표시하려면 타입 파라미터 이름 앞에 out을 넣어야 한다.
interface Producer<out T> {
    fun produce(): T
}

// 클래스의 타입 파라미터를 공변적으로 만들면 함수 정의에 사용한 파라미터 타입과 타입 인자의 타입이 정확히 일치하지 않아도
// 그 클래스의 인스턴스를 함수 인자나 반환값으로 사용할 수 있다.
open class Animal {
    fun feed() {}
}

// 이 타입 파라미터를 공변으로 지정하지 않았다.
//class Herd<T : Animal> {
class Herd<out T : Animal> { // 코드 1
    val size: Int get() = TODO()
    operator fun get(i: Int): T = TODO()
}

fun feedAll(animals: Herd<Animal>) {
    for (i in 0 until animals.size) animals[i].feed()
}

// 여기서 고양이 무리를 만들어서 관리한다고 가정해보자.
class Cat : Animal() {
    fun cleanLitter() {}
}

fun takeCareOfCats(cats: Herd<Cat>) {
    for (i in 0 until cats.size) cats[i].cleanLitter()
//    feedAll(cats) <- 컴파일 오류가 발생한다. 코드 2
}

// Herd 클래스의 T 타입 파라미터에 대해 아무 변성도 지정하지 않았으므로 Cat은 Animal 무리의 하위 클래스가 아니다.
// 이때 코드 1 처럼 Herd를 공변적인 클래스로 만들면 별도의 타입 캐스팅 없이 코드 2가 허용된다.
// 물론 모든 클래스를 공변적으로 만들 수는 없다. 그리고 타입 파라미터를 공변적으로 지정하면
// 클래스 내부에서 타입 안정성을 보장하기 위해 공변적 파라미터는 항상 생산만 할 수 있고, 소비는 할 수 없다.

// 클래스 멤버를 선언할 때 타입 파라미터를 사용할 수 있는 지점은 모두 in과 out 위치로 나뉜다.
// T라는 타입 파라미터를 선언하고 T를 사용하는 함수가 멤버로 있는 클래스가 있다고 가정해보자.
// T가 함수의 반환 타입에 쓰인다면 T는 out 위치에 있다. 이는 T 타입의 값을 생산한다.
// T가 함수의 파라미터 타입에 쓰인다면 T는 in위치에 있다. 이는 T 타입의 값을 소비한다.
// 즉 함수가 제네릭 타입을 파라미터로 받으면 소비, 제네릭 타입을 반환하면 생산인 것이다.
interface Transformer<T> {
    // in과 out 위치
    fun transform(t: T): T
}

// 그렇기 때문에 List<T>는 공변적으로 정의되어 있다.
// public actual interface List<out E> : Collection<E> {}
// 그 이유는 코틀린의 List는 읽기 전용이기 때문이다. 따라서 그 안에는 E를 반환하는 get은 있지만
// E를 리스트에 추가하는 로직은 없다. 따라서 List는 E에 대해 공변적이다.

// 생성자 파라미터는 이 두 위치 어느 쪽도 아니다. 따라서 out 타입 파라미터로 선언해도 다음과 같이 사용할 수 있다.
class OutTypeParameter<out T : Number>(val numbers: T)

// 하지만 val이나 var 키워드에 따라서 getter나 setter를 정의하는 것과 같다.
// 따라서 변경 가능 프로퍼티인 경우에는 in 위치에 해당하기 때문에 타입 파라미터를 out으로 표시할 수 없다.
class InTypeParameter<T : Number>(var numbers: T)

// 그리고 이런 규칙은 비공개 메서드의 파라미터에는 적용되지 않는다.
// 변성 규칙은 클래스 외부의 사용자가 클래스를 잘못 사용하는 일을 막기 위한 것이기 때문이다.
class PrivateInOut<out T : Animal>(private val leadAnimal: T, vararg val animals: T)
// 이 코드는 leadAnimal 프로퍼티가 비공개이기 때문에 T에 대해 공변적으로 선언해도 안전하다.
