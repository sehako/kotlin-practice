package part02.chapter13.section4

// DSL의 핵심은 깔끔한 구문이므로 코드에 사용되는 기호의 수를 줄이는 것이 핵심이다.
// 대부분의 내부 DSL은 메서드 호출을 연쇄시키는 형태로 만들어지기 때문에
// 메서드 호출 시 발생하는 잡음을 줄여주는 기능이 있다면 큰 도움이 될 수 있다. (간결한 람다 호출, 중위 함수 호출 등)
// kotest DSL을 보면서 중위 호출을 어떻게 활용하는지 살펴보자. (src/test/java/chapter13/section4/PrefixTest.kt 참고)

/*
class PrefixTest {
    @Test
    fun testKPrefix() {
        val s = "kotlin".uppercase()
        s should startWith("K")
    }
}
 */

// 이런 문법을 DSL에서 사용하려면 should 함수 선언 앞에 infix 변경자를 붙여야 한다.
/*
infix fun <T> T.should(matcher: Matcher<T>) {
   invokeMatcher(this, matcher)
}
 */

// should는 Matcher의 인스턴스를 요구한다. Matcher는 값에 대한 단언문을 표현하는 제네릭 인터페이스이다.
// startWith는 Matcher를 구현하며 어떤 문자열이 주어진 문자열로 시작하는지 검사한다.

/*
interface Matcher<T> {
    fun test(value: T)
}

fun startWith(prefix: String): Matcher<String> {
    return object : Matcher<String> {
        override fun test(value: String) {
            if(!value.startsWith(prefix)) {
                throw AssertionError("$value does not start with $prefix")
            }
        }
    }
}
 */

// 이렇게 중위 호출과 object로 정의한 싱글톤을 조합하면 DSL에 복잡한 문법을 도입하는 동시에 깔끔하게 만들 수 있다.
// 거기에 여전히 DSL은 정적 타입 지정 언어로 남기 때문에 함수와 객체의 잘못된 조합에 대한 컴파일러의 검토까지 받을 수 있다.
