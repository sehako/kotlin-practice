package chapter05.section4

// apply는 거의 with과 동일하게 작동하지만 유일한 차이는 apply는 항상 자신에 전달된 객체를 반환한다는 차이점이 있다.
fun alphabetApply() = StringBuilder().apply {
    for (letter in 'A'..'Z') {
        append(letter)
    }
    append("\nIt's all alphabet letters!")
}.toString()

// 또한 apply를 임의의 타입의 확장 함수로 호출할 수 있다.
// apply를 호출한 객체는 apply에 전달된 람다의 수신 객체가 된다.
val result: StringBuilder = StringBuilder().apply {

}

// 이는 인스턴스를 만들면서 즉시 프로퍼티 중 일부를 초기화해야 하는 경우 apply가 유용하다.
class ApplyPerson {
    var name: String = ""
    var age: Int = 0
    var email: String = ""
}

// apply를 사용하여 생성과 동시에 초기화
val person = ApplyPerson().apply {
    name = "홍길동"
    age = 30
    email = "hong@example.com"
}

// 참고로 코틀린은 표준 라이브러리로 특정 수신 객체를 가진 함수를 이미 지원하기도 한다.
fun alphabetWithBuildString() = buildString {
    for (letter in 'A'..'Z') {
        append(letter)
    }
    append("\nIt's all alphabet letters!")
}

// 이 외에도 읽기 전용 List, Set, Map을 생성하지만
// 생성 과정에서는 가변 컬렉션인 것처럼 다루고자 할 때 사용하는 빌더 함수가 존재한다.

val listBuild = buildList {
    add("one")
    add("two")
}

val setBuild = buildSet {
    add("one")
}

val setMap = buildMap {
    put("one", 1)
}
