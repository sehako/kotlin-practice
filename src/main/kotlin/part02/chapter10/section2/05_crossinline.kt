package part02.chapter10.section2

// crossinline은 inline 함수의 파라미터로 전달된 람다가 비지역 반환을 하지 못하도록 강제하는 키워드이다.
// 주로 인라인 함수 내부에서 다른 객체나 스레드를 람다로 전달해야 할 때 사용한다.

// 다음 코드를 보도록 하자.
inline fun executeAsync(block: () -> Unit) {
    // 에러 발생!
    // 인라인 람다 'block'은 비지역 반환이 가능하므로,
    // 나중에 실행될 수 있는 객체(Runnable) 안에는 넣을 수 없음.
    val runnable = Runnable {
//        block() <- 컴파일 에러 발생
    }
    runnable.run()
}

// 여기서 인라인 함수로 전달된 람다 블록이 내부로 인라이닝 되기 때문에 이는 곧 비지역 반환으로 인해 외부 함수를 종료시킬 수 있다는 것을 의미한다.
// 이때 안전하게 사용하려면 noinline을 사용하면 되지만 그런 경우에는 성능이 저하될 수 있다.
// 따라서 crossinline을 명시하여 람다의 코드는 여전히 인라이닝(복사) 하되,
// 비지역 반환만 불가능하게 강제하는 것이다.
inline fun executeAsyncCrossInline(crossinline block: () -> Unit) {
    val runnable = Runnable {
        block() // 이제 정상적으로 컴파일 및 실행 가능
    }
    runnable.run()
}

// 최종적으로 요약하면 다음과 같다.
// 1. 람다를 파라미터로 받는다 -> inline (기본)
// 2. 근데 람다를 객체에 담거나 다른 함수로 넘겨야 한다 -> 에러 발생
// 3. 성능이 중요해서 객체 생성을 피하고 싶다 -> crossinline (비지역 반환 포기)
// 4. 그냥 객체로 다루고 싶다 -> noinline (인라이닝 포기)
