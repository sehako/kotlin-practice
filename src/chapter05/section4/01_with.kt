package chapter05.section4

// with은 어떤 객체의 이름을 반복하지 않고도 그 객체에 대해 다양한 연산을 수행하는 기능이다.
fun alphabetFunction(): String {
    val result = StringBuilder()
    for (letter in 'A'..'Z') {
        result.append(letter)
    }
    result.append("\nIt's all alphabet letters!")
    return result.toString()
}

// 위 코드를 with으로 리팩터링하면 다음과 같다.
fun alphabetWithWith() = with(StringBuilder()) { // 식을 바로 반환하므로 식을 본문으로 하는 함수로 표현할 수 있다.
    for (letter in 'A'..'Z') {
        this.append(letter)
    }

    // this를 생략할 수도 있다.
    append("\nIt's all alphabet letters!")
    this.toString()
}

// with는 언어가 제공하는 자체 기능인 것 처럼 보이지만, 사실 파라미터가 2개 있는 함수이다.
// public inline fun <T, R> with(receiver: T, block: T.() -> R): R {...}
// 즉 StringBuilder가 receiver가 되는 것이고 그 이후 두 번째 파라미터가 람다로 전달된 것이다.
// 이를 통해 첫 번째 인자로 받는 객체를 두 번째 인자로 받은 람다의 수신 객체로 만든다.

// 메서드 이름이 충돌되는 경우에는 어떻게 해야할까? 이 경우에는 this참조에 레이블(@)을 명시하면 된다.
class WithOuter {
    fun thisReference() = with(StringBuilder()) {
        append("with")
        println(this@WithOuter.toString())
        toString()
    }
}
