package chapter9.section4

// 코틀린은 구조 분해 선언을 사용하여 복합적인 값을 분해하여 별도의 여러 지역 변수를 한 번에 초기화할 수 있다.
data class DestPoint(val x: Int, val y: Int)

fun destructuringDeclaration() {
    // x와 y 변수 선언 이후 DestPoint의 여러 컴포넌트로 초기화
    val (x, y) = DestPoint(1, 2)
}

// 내부에서 이런 구조 분해 선언의 각 변수를 초기화하고자 componentN이라는 함수를 호출한다.
// 여기서 N은 구조 분해 선언에 있는 변수 위치에 따라 붙는 번호이다.
// 데이터 클래스는 이런 구조 분해 선언을 컴파일러가 자동으로 만들어준다. 이를 직접 구현하면 다음과 같다.
class DestDeclarePoint(val x: Int, val y: Int) {
    operator fun component1() = x
    operator fun component2() = y
}

// 이는 함수에서 여러 값을 반환할 때 유용하다. 반환해야 하는 값에 대한 데이터 클래스를 정의하고 이를 반환하면 되기 때문이다.
data class NameComponents(val name: String, val extension: String)

// 단순하게 Pair나 Triple을 사용해도 되지만 그러면 코드의 표현력이 떨어지므로 이런 식으로 사용하도록 하자.
fun splitFilename(fullName: String): NameComponents {
    // split()의 결과도 구조 분해 할당을 사용했다.
    val (name, extension) = fullName.split(".")
    return NameComponents(name, extension)
    // 사용 예시: val (name, extension) = splitFilename()
}
// 참고로 배열이나 컬렉션에도 componentN 함수가 존재한다.
// 표준 라이브러리에는 맨 앞의 다섯 원소에 대한 componentN을 제공한다.

// 변수 선언이 들어갈 수 있는 장소라면 구조 분해 선언을 사용할 수 있다.
fun mapDestructingDeclarationExample(map: Map<String, String>) {
    // 자바와 다르게 코틀린은 Map을 직접 이터레이션 할 수 있다.
    // 또한 코틀린 라이브러리는 Map.Entry에 대한 확장 함수로 component1과 component2를 제공한다.
    for ((key, value) in map) {
        println("$key -> $value")
    }

    // 즉, 위 코드는 다음과 같다.
    for (entry in map.entries) {
        val key = entry.component1()
        val value = entry.component2()
    }

    // 람다 역시도 구조분해 선언을 쓸 수 있다.
    map.forEach { (key, value) -> println("$key -> $value") }
}

