package chapter13.section2

// 수신 객체 지정 람다는 구조화된 API를 만들 때 활용하는 기능이다.
// buildList, buildString, with, apply 등 이미 수신 객체 람다를 활용한 바 있다. 이를 직접 정의하면 다음과 같다.
fun buildString(
    builderAction: (StringBuilder) -> Unit
): String {
    val sb = StringBuilder()
    builderAction(sb)
    return sb.toString()
}

fun buildStringExample() {
    val str: String = buildString {
        it.append("Hello, ")
        it.append("world!")
    }
}

// 람다 본문에서 매번 it을 사용해 StringBuilder 인스턴스를 참조해야 한다.
// it을 접두사로 넣지 않고 append만 호출하려면 람다를 수신 객체 지정 람다로 바꿔야 한다.
fun buildStringWithReceiver(
    builderAction: StringBuilder.() -> Unit // 수신 객체가 지정된 함수 타입의 파라미터 선언
): String {
    val sb = StringBuilder()
    sb.builderAction() // 인스턴스를 람다의 수신 객체로 전달
    return sb.toString()
}

fun buildStringWithReceiverExample() {
    val str: String = buildStringWithReceiver {
        append("Hello, ") // it이 없어도 append를 호출할 수 있다.
        append("world!")
//        this.append("!") <- 수신 객체 지정 람다의 인자는 확장 함수 타입의 파라미터와 대응한다.
        // 따라서 본문 내에서는 수신 객체(sb)가 암시적 수신 객체(this)가 된다.
    }
}

// 수신 객체 지정 파라미터는 다음과 같은 문법이다. 수신 객체 타입.(파라미터 타입) -> 반환 타입
// 또한 수신 객체 지정 람다는 StringBuilder에 있는 메서드처럼 호출하는 것을 볼 수 있다.

// 수신 객체 지정 람다를 변수에 저장하는 예시를 보도록 하자.
val appendExcl: StringBuilder.() -> Unit = { append("!") }

fun buildStringWithReceiverVariableExample() {
    val sb = StringBuilder("Hi")
    sb.appendExcl()
    println(sb)
    // 수신 객체 지정 람다는 일반 람다를 저장해서 넘기는 것과 똑같이 보인다.
    // 따라서 람다가 전달되는 함수의 시그니처를 살펴봐야 어떤 타입의 수신 객체를 요구하는 지 알 수 있다.
    buildStringWithReceiver(appendExcl)
}

// 표준 라이브러리의 buildString 구현은 구현 코드보다 훨씬 짧다.
/*
@kotlin.internal.InlineOnly
public inline fun buildString(builderAction: StringBuilder.() -> Unit): String {
    contract { callsInPlace(builderAction, InvocationKind.EXACTLY_ONCE) }
    return StringBuilder().apply(builderAction).toString()
}
apply 함수에게 인자로 넘기는 것을 볼 수 있다. 이는 인자로 받은 람다나 함수를 호출하면서
자신의 수신 객체를 람다나 함수의 암시적 수신 객체로 사용한다. with과 함께 두 구현을 살펴보자.
 */

/*
@kotlin.internal.InlineOnly
public inline fun <T> T.apply(block: T.() -> Unit): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    block()
    return this
}

@kotlin.internal.InlineOnly
public inline fun <T, R> with(receiver: T, block: T.() -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return receiver.block()
}
 */
// 기본적으로 apply와 with는 자신이 제공받은 수신 객체를 가지고 확장 함수 타입의 람다를 호출한다.
// apply는 수신 객체 타입에 대한 확장 함수로 선언되었기 때문에 수신 객체의 메서드 처럼 호출되며 이를 암시적 인자(this)로 받는다.
// 반면 with는 수신 객체를 첫 번째 파라미터로 받는다. 코틀린에서는 이 두 함수를 자주 사용하여 코드를 간결하게 만드는 경우가 많으므로
// 이 두 함수에 익숙해지는 것이 좋다.
