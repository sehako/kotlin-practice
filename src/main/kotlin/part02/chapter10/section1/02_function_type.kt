package part02.chapter10.section1

// 자동 SAM 변환을 통해 코틀린 람다로 자바 함수형 인터페이스를 구현할 수 있다.
// 이는 자바에서 정의된 고차 함수를 문제없이 사용할 수 있다는 것이다.

fun processTheAnswer(f: (Int) -> Int) {

}

// 이는 자바에서 다음과 같이 호출할 수 있다.
// processTheAnswer(number -> number + 1)
// 앞선 챕터에서 정리했던 것 처럼 코틀린의 함수 타입을 자바에서 호출할 때 주의해야 할 점은
// Unit 타입을 반환하는 함수 타입에 대해서 자바 코드에서는 void를 반환할 수 없다는 것이다.

/*
내부에서 코틀린 타입은 일반 인터페이스이다. 함수 타입의 변수는 FunctionN(N은 파라미터 개수) 인터페이스를 구현한다.
내부 구현은 다음과 같다.
public interface Function1<in P1, out R> : Function<R> {
    public operator fun invoke(p1: P1): R
}
즉, 모든 함수 타입은 내부적으로 다음과 같다.
 */
fun processTheAnswerFunction(f: Function1<Int, Int>) {}

// FunctionN 인터페이스는 컴파일러가 생성한 합성 타입이다. 코틀린 표준 라이브러리에서 이들의 정의를 찾을 수 없다.
// 컴파일러는 필요할 때 이런 인터페이스들을 생성한다.
// 따라서 파라미터의 개수 제한 없이 파라미터를 사용하는 함수에 대한 인터페이스를 사용할 수 있다.

// 파라미터를 함수 타입으로 선언할 때도 기본값을 정의할 수 있다.
// 예를 들어 어떤 연산이 있는데 람다를 별도로 넘겨주지 않으면 기본적으로 합 연산을 한다고 가정해보자.
fun calculateTwoNumber(function: (x: Int, y: Int) -> Int = { x, y -> x + y }) {
    println(function(1, 2))
}

// 이런 방식은 기본 동작으로도 충분하지만 때때로 동작을 커스텀 해야 하는 상황에서 매우 유용하다.
// 다른 접근 방법으로 null이 될 수 있는 함수 타입을 사용할 수도 있다.
// null이 될 수 있는 함수 타입으로 함수를 받으면 그 함수를 직접 호출할 수 없다는 것을 유의해야 한다.
fun nullableFunction(callback: (() -> Unit)?) {
    // 따라서 다음과 같이 null 여부를 검사할 수 있다.
    if (callback != null) callback()
    // 하지만 함수 타입이 invoke()를 가지고 있는 인터페이스이기 때문에 안전한 호출 구문으로 호출하는 것이 더 간결하다.
    callback?.invoke() ?: println("callback is null") // 엘비스 연산자도 가능하다.
}
