package part02.chapter11.section3

// 여러 제네릭 타입을 조합한 타입을 다룰 때 타입 시그니처의 모든 의미를 추적하기 귀찮을 수 있다. 
// 예를 들어 List<(String, Int) -> String> 타입 컬렉션이 구체적으로 어떤 역할을 하는지 알기 힘들다. 
// 코틀린은 이러한 상황에 대해서 타입 별칭을 사용할 수 있게 해준다. 
// 타입 별칭은 기존 타입에 대해 다른 이름을 부여한다. typealias 키워드 뒤에 별칭을 적어 타입 별칭 선언을 할 수 있다.
typealias NameCombiner = (String, String, String, String) -> String

val authorsCombiner: NameCombiner = { first, second, third, fourth -> "$first $second $third $fourth" }

// 이런 식으로 타입 별칭을 도입하면 함수형 타입에 대해서 좀 더 새로운 맥락을 부여할 수 있다.
// 그리고 이는 컴파일러의 관점에서 컴파일 하는 동안 원래의 타입으로 치환되기 때문에 별도의 제약이나 변경이 없다.
// 따라서 타입 별칭은 유용한 짧은 표기를 제공할 뿐 타입 안정성을 추가해주지는 못한다. 다음 상황을 보자
typealias ValidatedInput = String

fun save(v: ValidatedInput) = println("Saved $v")
// 타입 별칭에 따르면 검증된 어떠한 값을 원하지만 사실 String 타입이면 모두 허용된다.

// 이러한 경우에는 최소한의 부가 비용으로 타입 안정성을 추가할 수 있는 인라인 클래스를 활용하도록 하자.
@JvmInline
value class Validated(val value: String) {
    init {
        require(value.isNotEmpty()) { "Validated string cannot be empty" }
    }
}

