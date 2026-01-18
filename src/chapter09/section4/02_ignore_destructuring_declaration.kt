package chapter09.section4

// 컴포넌트가 여러 개 있는 객체에 대해 구조 분해 선언을 사용할 때는 변수 중 일부가 필요 없는 경우가 있다.
data class MultiPropertyPerson(
    val firstName: String,
    val lastName: String,
    val age: Int,
    val city: String
)

fun printPersonInformation(person: MultiPropertyPerson) {
    val (firstName, lastName, age, city) = person
    println("$firstName: (age: $age)")

    // 위와 같은 경우는 코드의 가독성이 좋지 않다.
    // 이를 해결하기 위해서 우선 구조 분해 선언에서 사용하지 않는 마지막 선언은 제거할 수 있다.
    val (firstName1, lastName1, age1) = person

    // 하지만 lastName은 저런 식으로 없앨 수 없다. 이 경우에는 _문자로 공허한 할당을 하도록 만들 수 있다.
    val (firstName2, _, age2) = person

    // 참고로 _는 구조 분해 선언 내에서도 여러 개 명시할 수 있다.
    val (firstName3, _, age3, _) = person

    /*
    여기서 구조 분해 선언의 단점을 알 수 있다. 구조 분해 선언은 내부적으로 변수의 순서에 따른 componentN 함수 호출로 이루어진다.
    이 말은 즉 객체의 필드 순서대로 호출되기 때문에 리팩터링을 하면서 필드 순서를 변경하면 문제가 발생할 수 있다.
    위 경우에서 firstName과 lastName은 둘 다 String 타입이므로 순서를 자칫 변경하면 실제 서비스에서 성과 이름이 뒤바뀌게 된다.
    따라서 구조 분해 선언은 작은 컨테이너 클래스나 변경 가능성이 매우 적은 클래스에만 사용하는 것이 바람직하다.
    (이름 기반 구조 분해 선언이 논의중이라고 한다.)
     */
}

// 그리고 구조 분해 선언은 여러 지역 변수를 한 번에 만드는 문법 설탕이라,
// 라이프사이클과 규칙이 복잡한 프로퍼티/필드에는 허용되지 않고 로컬 스코프에서만 허용된다.
val test: MultiPropertyPerson = MultiPropertyPerson(
    "Eric",
    "Evans",
    29,
    "Chicago"
)

//val (firstName, lastName, age, city) = test <- 컴파일 오류 발생
// Destructuring declarations are only allowed for local variables/values
