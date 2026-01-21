package chapter10.section2

// 사람의 리스트를 필터링하는 두 가징 방법을 살펴보자.
data class FilterPerson(val name: String, val age: Int)

val people = listOf(FilterPerson("Alice", 29), FilterPerson("Bob", 31))

fun filterPersonExample1() {
    people.filter { it.age < 30 }
    // 이를 람다식을 사용하지 않게 작성하면 다음과 같다.
    val result = mutableListOf<FilterPerson>()
    for (person in people) {
        if (person.age < 30) result.add(person)
    }

    // 코틀린에서 filter 함수는 인라인 함수다.
    // 따라서 filter의 바이트 코드는 그 함수에 전달된 람다 본문의 바이트코드와 함께 filter를 호출한 위치에 인라이닝된다.
    // 즉 두 코드의 바이트 코드는 유사하다. 코틀린이 제공하는 함수 인라이닝 덕분에 성능을 고려하지 않아도 된다.
}

// filter와 map을 연쇄적으로 사용하면 마찬가지로 추가 객체나 클래스 생성은 없다.
// 하지만 중간에 필터링한 결과를 저장하는 리스트를 만든다.
// 따라서 처리할 코드가 많아지면 중간 리스트를 사용하는 부가 비용을 고려해서 asSequence를 활용하자.

fun filterMapExample() {
    people.filter { it.age < 30 }.map(FilterPerson::name)

}

// 인라인 결정 가이드
// inline 키워드를 무조건적으로 사용하는 것이 좋은 것은 아니다.
// 바이트코드를 실제 기계어로 변역하는 과정(JIT)에서 JVM이 가장 이익이 되는 방향으로 호출을 인라이닝한다.
// 예를 들어 일반 함수 호출은 JVM이 인라이닝을 지원한다.
// 따라서 바이트코드에서는 각 함수 구현이 정확히 한 번만 있으면 되고, 그 함수를 호출하는 모든 부분에 따로 코드를 중복할 필요가 없다.
// 반면 코틀린 인라인 함수는 바이트코드에서 각 함수 호출 지점을 함수 본문으로 대치하기 때문에 코드 중복이 생긴다.

// 람다를 인자로 받는 경우에는 이익이 더 많다. 인라이닝을 통해 상당히 많은 부가 비용을 없앨 수 있으며,
// 람다를 표현하는 클래스와 람다 인스턴스에 해당하는 객체를 만들 필요도 없어진다.
// 그리고 현재의 JVM은 함수 호출과 람다를 자동으로 최적화해주지 않는다.
// 그리고 인라이닝을 사용하면 일반 람다에서는 사용할 수 없는 몇 가지 기능을 사용할 수 있다.

// 하지만 inline 변경자를 명시할 때는 코드 크기에 주의해야 한다.
// 인라이닝하는 함수가 큰 경우 함수의 본문에 해당하는 바이트코드를 모든 호출 지점에 복사해 넣으면
// 바이트코드가 전체적으로 커질 수 있다.
