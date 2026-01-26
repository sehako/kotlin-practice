package part01.chapter05.section1

// 코틀린의 람다식은 항상 중괄호로 둘러싸여 있다.
// 그리고 인자 목록 주변에 괄호가 없으며, 화살표로 인자 목록과 람다 본문을 구분한다.
val sum = { x: Int, y: Int -> x + y } // 람다식을 변수에 저장할 수도 있다. 이를 일반 함수와 마찬가지로 다룰 수도 있다.

fun main() {
    sum(1, 2)

    // 람다식을 다음과 같이 직접 호출할 수도 있다.
    // 물론 람다 본문을 직접 실행하는 것이 낫다.
    // { println("Hello, Kotlin!") }()

    // 코드의 일부분을 블록으로 둘러싸 실행하고자 한다면 인자로 받은 람다를 실행하는 run 함수를 사용한다.
    run { println("Hello, Kotlin!") }

    // 이는 식이 필요한 부분에서 코드 블록을 실행하고자 할 때 유용하다.
    val myFavoriteNumber = run {
        println("I like 7!")
        6 // return 이 필요하지 않다.
    }

    println(myFavoriteNumber) // 6

    // maxByOrNull의 정석 람다식은 다음과 같다.
    // arrayListOf<Int>().maxByOrNull { it } 이를 람다로 풀어쓰면
    arrayListOf<Int>().maxByOrNull({ num: Int -> num })
    // 이는 코드의 번잡함이 존재한다. 또한 컴파일러가 문맥으로부터 유추할 수 있는 인자 타입을 굳이 적을 필요는 없다.
    // 이를 단계적으로 간소화 하면 다음과 같다.
    // 우선, 코틀린은 함수 호출 시 맨 뒤에 있는 인자가 람다식이면 그를 괄호 바깥으로 빼낼 수 있다.
    arrayListOf<Int>().maxByOrNull() { num: Int -> num }

    // 크리고 람다가 어떤 함수의 유일한 인자이며, 괄호 뒤에 람다를 썼다면 호출 시 빈 괄호를 없앨 수 있다.
    arrayListOf<Int>().maxByOrNull { num: Int -> num }
    // 이렇게 인자가 여럿 있는데, 마지막 인자만 람다인 경우에는 람다를 바깥으로 뺴내는 것이 코틀린에서 가장 좋은 스타일이다.
    // 참고로 둘 이상의 람다를 인자로 받는 경우에는 람다를 괄호 바깥으로 빼낼 수 없다.

    // 마지막 인자가 람다인 경우를 보기 위해 joinToString()을 살펴보자.
    arrayListOf("a", "b", "c").joinToString(separator = " ", transform = { name: String -> name })
    arrayListOf("a", "b", "c").joinToString(separator = " ") { name: String -> name }

    // 추가로 파라미터 타입을 없앨 수 있다.
    arrayListOf<Int>().maxByOrNull { num -> num } // 컴파일러는 람다 파라미터의 타입을 추론할 수 있다.

    // 마지막으로 람다의 파라미터 이름을 디폴트 이름인 it으로 바꾸면 람다식을 더 간단하게 만들 수 있다.
    // 람다의 파라미터가 하나뿐이고, 타입을 컴파일러가 추론할 수 있는 경우 it을 쓸 수 있다.
    arrayListOf<Int>().maxByOrNull { it }

    // 마지막으로 람다를 변수에 저장할 때는 파라미터의 타입을 추론할 문맥이 존재하지 않으므로 파라미터 타입을 명시해야 한다.
    val getMax = { num: Int -> num }
    arrayListOf<Int>().maxByOrNull(getMax)
}
