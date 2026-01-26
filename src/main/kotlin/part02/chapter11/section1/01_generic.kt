package part02.chapter11.section1

// 제네릭을 사용하면 타입 파라미터를 받는 타입을 정의할 수 있다. 이는 인스턴스가 만들어질 때 타입 파라미터를 구체적인 타입 인자로 치환한다.
// 컬렉션을 다루는 라이브러리 함수는 대부분 제네릭 함수다.
// fun <T> List<T>.alice(indices: IntRange): List<T> <- 제네릭 함수는 fun<> 으로 선언된다.
// 함수의 타입 파라미터 T가 수신 객체와 반환 객체에 사용된다.
// 이런 함수는 이후 호출될 때 타입을 명시할 수 있지만 대부분 상황에서 컴파일러가 타입 인자를 추론해준다.
fun genericFunction() {
    val list = listOf(1, 2, 3)
    // 두 호출의 결과 타입은 모두 List<Int>이다.
    list.slice<Int>(1..2)
    list.slice(1..2)
}

// 제네릭 확장 프로퍼티를 선언할 수도 있다.
val <T> List<T>.penultimate: T
    get() = this[size - 2]

// 제네릭 클래스는 자바와 마찬가지로 <>를 클래스나 인터페이스 이름 뒤에 명시하면 된다.
interface GenericList<T> {
    // 인터페이스에서는 T를 일반 타입처럼 사용할 수 있다.
    operator fun get(index: Int): T
}

// 제네릭을 가진 클래스나 인터페이스의 확장 시 타입 인자를 지정해야 한다.
class StringList : GenericList<String> {
    // 타입이 String으로 정해졌으므로 메서드 시그니처도 String으로 명시되었다.
    override fun get(index: Int): String = TODO()
}

// 해당 클래스가 제네릭을 사용한다면 타입 파라미터를 넘길 수도 있다.
class ArrayList<A> : GenericList<A> {
    override fun get(index: Int): A = TODO("Not yet implemented")
}
