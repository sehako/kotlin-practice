package part02.chapter09.section2

// 정렬이나 최대 최소 비교에 있어서 자바는 Comparable를 클래스에서 구현하거나 Comparator를 람다로 구현한다.
// 자바는 Comparable구현 클래스에 대해 이를 비교 연산자로는 호출할 수 없고, compareTo()를 명시적으로 호출해야 한다.
// 반면 코틀린은 Comparable 구현에 있어서 비교 연산자를 사용했을 때 compareTo 호출로 컴파일한다.
class ComparePerson(val firstName: String, val lastName: String) : Comparable<ComparePerson> {
    // 코틀린 compareTo 메서드 구현 예시
    override fun compareTo(other: ComparePerson): Int {
        return compareValuesBy(
            this,
            other,
            // 인자로 받은 함수나 프로퍼티 참조를 차례로 호출하며 값을 비교한다.
            // 첫 번째 값이 같으면 비교할 값을 전달하는 식으로 여러 인자를 전달할 수 있다.
            ComparePerson::lastName,
            ComparePerson::firstName
        )
    }
    // 필드 직접 비교보다 성능이 떨어지는 코드이다. 하지만 처음에는 성능을 고려하지 말고 이해와 간결성에 초점을 맞추고,
    // 그 이후 성능을 개선하는 식으로 개발하는 것이 좋다.
}
// 여기서 한 가지 의문이 operator가 없는데 어떻게 비교 연산에 대해 compareTo가 호출되는 것인가?였다.
// 이는 코틀린의 Comparable 인터페이스에 있는 compareTo 추상 메서드가 operator 변경자가 명시되어 있기 때문이다.
// 이 경우에는 하위 클래스에서 오버라이드할 때 operator 변경자를 붙일 필요가 없다.

// Comparable를 구현하는 모든 자바 클래스를 코틀린에서는 비교 연산자 구문으로 비교가 가능하다.
fun stringCompare() {
    println("Kotlin" < "Java")
}
