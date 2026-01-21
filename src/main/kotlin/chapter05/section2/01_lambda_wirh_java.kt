package chapter05.section2

// 코틀린 람다와 자바 API는 호환이 가능하다.
// 자바에서 단일 추상 메서드 (SAM, Single Abstract Method) 인터페이스
// 또는 함수형 인터페이스는 람다를 사용할 수 있다. 코틀린 역시 이러한 함수형 인터페이스에 람다를 명시할 수 있다.

// 함수형 인터페이스를 파라미터로 받는 자바 메서드를 코틀린 람다로 전달하는 예시
fun lambdaWithJavaMethod() {
    val javaClass = LambdaWithJava()
    // postponeComputation은 Runnable을 마지막 인자로 받는다.
    // 따라서 다음과 같이 코틀린 람다를 넘겨줄 수 있다.
    javaClass.postponeComputation(1000) { // 컴파일러가 자동으로 Runnable 인스턴스로 변환한다.
        println(42)
    }

    // Runnable을 명시적으로 구현하는 익명 객체를 만들어 전달할 수도 있다.
    javaClass.postponeComputation(1000, object : Runnable {
        override fun run() {
            println(42)
        }
    })
    // 하지만 이 둘은 차이가 있다. 명시적으로 객체를 선언하면 매번 호출될 때마다 새 인스턴스가 생긴다.
    // 람다를 사용하면 자신이 정의된 함수의 변수(람다 외부의 어떤 변수)에 접근하지 않는다면 함수가 호출될 때마다 재사용된다.
    // 즉 첫 번째 람다식은 전체 프로그램 통틀어 Runnable 인스턴스가 단 하나 만들어진다.
}

// 물론 다음과 같이 변수를 캡처한 상황이라면 호출마다 id를 필드 값으로 저장하는 새로운 Runnable 인스턴스가 생성된다.
fun handleComputationWithVariableCapture(id: String) {
    val javaClass = LambdaWithJava()
    javaClass.postponeComputation(1000) { println(id) }
}
