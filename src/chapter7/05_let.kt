package chapter7

// let을 사용하면 null이 될 수 있는 식을 더 쉽게 다룰 수 있다.
// 안전한 호출 연산자와 함께 사용하여 원하는 식을 평가하여 결과가 null인지 검사한 다음에
// 그 결과를 변수에 넣는 작업을 간단한 식을 사용해 한꺼번에 처리할 수 있다.

// 흔한 let 사용 예시: null이 될 수 있는 값을 null이 아닌 값만 인자로 받는 함수에 넘기는 경우

fun sendEmailNotNull(email: String) {}

fun getRandomEmail(): String? = null

fun letExample() {
    val email: String? = null
    // let이 없다면 null이 될 수 있는 타입에 대한 검사가 필수적이다.
    if (email != null) {
        sendEmailNotNull(email)
    }

    // 위 과정을 let 함수로 간결하게 만들 수 있다.
    // let은 자신의 수신 객체를 인자로 전달받은 람다에 넘긴다.
    // null이 될 수 있는 값에 대해 안전한 호출 구문을 사용해 let을 호출하되, null이 아닌 타입을 인자로 받는 람다를 let에 전달한다.
    // 따라서 null이 될 수 있는 타입의 값이 null이 될 수 없는 타입의 값으로 바뀌어 람다에 전달된다.
    // null이 아닐 때만 let이 호출되기 때문이다.
    email?.let { email: String -> sendEmailNotNull(email) }
    // 위 람다 표현식을 간소화 하는 과정 또한 보도록 하자.
    email?.let { sendEmailNotNull(it) }
    email?.let(::sendEmailNotNull)

    // 이런 식으로 아주 긴 식이 있고, 그 값이 null이 아닐 때 수행해야 하는 로직이 있는 경우 let을 사용하면 편리하다.
    // let을 사용하면 긴 식의 결과를 저장하는 변수를 따로 만들 필요가 없다.
    val randomEmail = getRandomEmail()
    if (randomEmail != null) sendEmailNotNull(randomEmail)
    // 위 코드와 아래 코드의 간결성을 비교해보자.
    getRandomEmail()?.let(::sendEmailNotNull)

    // 여러 값이 null인지 검사해야 한다면 let을 중첩해서 처리할 수 있지만 이 경우에는 코드의 가독성을 해칠 수 있으므로
    // if를 활용한 null 검사가 더 나은 선택이 될 수 있다.
}

/*
코틀린 영역 함수 비교: with, apply, let, run, also

| 함수            | x를 어떤 방식으로 참조하는가 | 반환값        |
|-----------------|------------------------------|---------------|
| x.let { ... }   | it                           | 람다의 결과   |
| x.also { ... }  | it                           | x             |
| x.apply { ... } | this                         | x             |
| x.run { ... }   | this                         | 람다의 결과   |
| with(x) { ... } | this                         | 람다의 결과   |
적합한 작업은 다음과 같다.
- let: null이 아닌 경우에만 코드 블록 실행, 어떤 식의 결과를 변수에 담되 그 영역을 한정
- apply: 빌더 스타일의 API를 사용해 객체 프로퍼티를 설정
- also: 어떤 동작 실행 후 원래의 객체를 다른 연산에 사용
- with: 하나의 객체에 대해 이름을 반복하지 않으면서 여러 함수 호출을 그룹화
- run: 객체 설정 이후 별도 결과를 돌려주고자 할 때
 */
