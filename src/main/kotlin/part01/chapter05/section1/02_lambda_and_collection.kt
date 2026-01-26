package part01.chapter05.section1

// 람다로 인해 코틀린은 컬렉션을 다룰 때 강력한 기능을 제공한다.

// 이 데이터 클래스로 이루어진 리스트에서 가장 나이가 많은 사람을 찾고자 한다고 가정해보자.
data class PersonForCollection(val name: String, val age: Int)

// 컬렉션을 for 루프로 직접 검색하기
fun findTheOldest(people: List<PersonForCollection>) {
    var maxAge = 0
    var theOldest: PersonForCollection? = null
    for (person in people) {
        if (person.age > maxAge) {
            maxAge = person.age
            theOldest = person
        }
    }
    println(theOldest)
}

// 코틀린에서는 위와 같은 기능을 하는 더 좋은 방법인 표준 라이브러리 함수가 있다.
fun findTheOldest2(people: List<PersonForCollection>) {
    // age를 비교해서 값이 가장 큰 원소 찾기
    println(people.maxByOrNull { it.age })
    // maxByOrNull은 모든 컬렉션에서 사용할 수 있으며, { it.age }는 선택자 로직을 구현한 것이다.
    // 선택자는 어떤 원소를 인자로 받아 비교에 사용할 값을 반환한다.
    // 람다가 인자를 하나만 받고 그 인자에 구체적 이름을 붙일 필요가 없으면 it이라는 암시적 이름을 사용할 수 있다.

    // 람다가 단순히 함수나 프로퍼티에 위임할 경우에는 멤버 참조를 사용할 수 있다.
    people.maxByOrNull(PersonForCollection::age)
}