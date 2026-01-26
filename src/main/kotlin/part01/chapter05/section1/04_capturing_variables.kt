package part01.chapter05.section1

// 함수 안에 익명 내부 클래스를 선언하면 그 클래스 안에서 함수의 파라미터와 로컬 변수를 참조할 수 있다.
// 람다에서도 이와 같은 일을 할 수 있다. 람다를 함수 안에서 정의하면 함수의 파라미터와 로컬 변수까지 활용이 가능하다.

fun printMessagesWithPrefix(messages: Collection<String>, prefix: String) {
    messages.forEach {
        // 람다 범위에서 함수의 파라미터인 prefix를 사용
        println("$prefix $it")
    }
}

// 자바와 다르게 코틀린에서는 람다 안에서 final 변수가 아닌 변수에 접근할 수 있다는 점이다.
// 람다에서 바깥의 변수를 변경할 수 있다.

fun printProblemCounts(responses: Collection<String>) {
    // 람다 범위 바깥의 변경 가능한 변수
    var clientErrors = 0
    var serverErrors = 0

    responses.forEach {
        if (it.startsWith("4")) {
            // 람다 범위 내에서 수정이 가능하다.
            // 람다에서 접근할 수 있는 외부 변수를 '람다가 캡처한 변수'라고 부른다.
            clientErrors++
        }
        if (it.startsWith("5")) {
            serverErrors++
        }
    }
}

// 기본적으로 함수에 정의된 로컬 변수의 생명주기는 함수와 동일하다.
// 하지만 어떤 함수가 자신의 로컬 변수를 캡처한 람다를 반환하거나 다른 변수에 저장하면 이 생명주기가 달라질 수 있다.
// 캡처한 변수가 있는 람다를 저장해서 함수가 끝난 뒤 실행해도 람다의 본문 코드는 여전 히 캡처한 변수에 접근할 수 있다.
// 이것이 가능한 이유는 final 변수는 람다 코드를 변수 값과 함께 저장하고,
// final이 아닌 변수는 변수를 특별한 래퍼로 감싸서 나중에 접근할 수 있게 만든 다음 래퍼에 대한 참조를 람다와 함께 저장하기 때문이다.
// 이는 자바에서 클래스를 이용해 변경 가능한 변수를 캡처하는 방식을 응용한 것이다.
// 코틀린 코드로 래퍼 클래스를 활용해 변경 가능한 변수를 캡처하는 방식을 살펴보자.
class Ref<T>(var value: T)

fun refTest() {
    val counter = Ref(0)
    // 변경 불가능하지만 이것이 참조하는 필드의 값은 변경이 가능하다.
    val increment = { counter.value++ }

    // 위와 같은 과정을 코틀린은 다음과 같이 간소화 한 것이다.
    var counter2 = 0
    val increment2 = { counter2++ }
}

// 주의해야 할 점은 람다를 이벤트 핸들러나 다른 비동기적으로 실행되는 코드로 활용하는 경우
// 로컬 변수 변경은 람다가 실행될 때만 일어난다.
fun tryToCountButtonClicks(button: LambdaButton): Int {
    var clicks = 0
    button.setOnClickListener { clicks++ }
    return clicks // 항상 0을 반환
}
// onCLick 핸들러는 호출될 때마다 clicks의 값을 증가시키지만 그 값의 변경을 관찰할 수 없다. (하지만 계속 증가는 한다)
// 핸들러는 tryToCountButtonClicks가 clicks를 반환한 다음에 호출되기 때문에 초기값인 0이 항상 반환된다.
// 이 함수를 제대로 구현하려면 클릭 횟수를 세는 카운터 변수를 함수 바깥으로 옮겨야 한다. (클래스의 프로퍼티 위치, 반응형 패턴 등)
