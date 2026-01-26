package part02.chapter12.section1

import part02.chapter12.jkid.JsonExclude
import part02.chapter12.jkid.JsonName
import part02.chapter12.jkid.serialization.serialize

// 어노테이션을 사용하는 고전적인 예제로 객체 직렬화 제어가 있다.
// 코틀린 객체를 JSON으로 변환하는 코틀린 라이브러리로 kotlinx.serialization,
// 그 외데 Jackson, GSON 등이 자바 객체를 JSON으로 변환하기 위해 설계된 라이브러리도 코틀린과 완전히 호환된다.
// 여기서 제이키드라는 순수 코틀린 직렬화 라이브러리를 프로젝트에 삽입하여 살펴보도록 하겠다.
// 링크: https://github.com/Kotlin/kotlin-in-action-2e-jkid

data class SerialPerson(val name: String, val age: Int)

fun serializePerson() {
    val person = SerialPerson("Alice", 29)
    val json = """{"age": 29, "name": "Alice"}"""

    println(json == serialize(person)) // true
}

// 제이키드 라이브러리는 기본적으로 모든 프로퍼티를 직렬화한다.
// 이때 제이키드는 다음 어노테이션을 통해서 동작을 변경할 수 있다.
/*
- @JsonExclude: 어노테이션을 사용하면 직렬화나 역직렬화할 때 무시해야 하는 프로퍼티를 표시할 수 있다.
- @JsonName: 어노테이션을 사용하면 프로퍼티를 표현하는 키/값 쌍의 키로 프로퍼티 이름 대신 어노테이션이 지정한 문자열을 쓰게 할 수 있다.

이를 활용하면 다음과 같다.
*/
data class JsonAnnotationPerson(
    @JsonName("alias") val firstName: String,
    @JsonExclude val age: Int? = null
)

// 직렬화 대상에서 제외할 age 프로퍼티는 반드시 기본값을 지정해야만 한다.
// 기본값을 지정하지 않으면 역직렬화할 때 Person의 인스턴스를 새로 만들 수 없기 때문이다.

// 이는 제이키드에서 직접 선언한 어노테이션이다. 코틀린에서는 어노테이션을 다음과 같이 선언할 수 있다.
// annotation class JsonExclude
// 어노테이션 클래스는 선언이나 식과 관련 있는 메타데이터의 구조만 정의하기 때문에 내부에 아무 코드도 있을 수 없다.
// 그런 이유로 컴파일러는 어노테이션 클래스에서 본문을 정의하지 못하게 막는다.

// 파라미터가 있는 어노테이션을 정의하려면 어노테이션 클래스의 주 생성자에 파라미터를 선언하면 된다.
// annotation class JsonName(val name: String) // 어노테이션 클래스의 경우 반드시 모든 파라미터가 val 이어야 한다.
