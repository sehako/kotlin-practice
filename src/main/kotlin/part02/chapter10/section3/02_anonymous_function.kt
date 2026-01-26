package part02.chapter10.section3

// 비로컬 return은 람다 안에 return이 여럿 들어가야 하는 경우 불편해진다.
// 코틀린에서는 익명 함수에서 return 식을 쓸 수 있도록 만들었다.

data class AnonymousPerson(val name: String, val age: Int)

fun lookForAliceAnonymousFunction(people: List<AnonymousPerson>) {
    // 함수 이름을 생략하고 파라미터의 이름과 함께 함수를 정의
    people.forEach(fun(person) {
        if (person.name == "Alice") return
        println("${person.name} is not Alice")
    })

    // 또 다른 예시를 보도록 하자.
    people.filter(fun(person): Boolean {
        return person.age > 30
    })
    // 익명 함수도 일반 함수와 같은 반환 타입 지정 규칙을 따른다.
    // 블록이 본문인 익명 함수는 반환 타입을 명시해야 하지만 식을 본문으로 하는 익명 함수의 반환 타입은 생략할 수 있다.
    people.filter(fun(person) = person.age > 30)

    // 익명 함수 안에서 레이블이 붙지 않은 return 식은 익명 함수 자체를 반환시킬 뿐 이를 둘러싼 다른 함수를 반환시키지 않는다.
    // 이는 return이 fun 키워드로 정의된 가장 안쪽 함수를 반환한다는 규칙에 의거한 것이다.
    // 람다는 fun으로 정의되지 않으므로 람다 본문의 return이 바깥 함수를 반환시키지만,
    // 익명 함수는 fun으로 정의하므로 함수 자신이 가장 안쪽의 fun이 되기 때문에 이런 차이가 발생하는 것이다.
    // 참고로 인라인 함수에 의해 인라이닝될 때 익명 로컬 함수가 된다.
}

// 익명 함수는 인라인 함수가 아닌 경우에도 return을 쓸 수 있다.
fun noInlineAnonymousFunction(operation: () -> Unit) {
}

fun returnInAnonymousFunctionExample() {
    noInlineAnonymousFunction(fun() {
        return
    })
}

