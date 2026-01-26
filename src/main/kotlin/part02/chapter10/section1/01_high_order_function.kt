package part02.chapter10.section1

// 고차 함수는 다른 함수를 인자로 받거나 함수를 반환하는 함수이다.
// 코틀린에서는 람다나 함수 참조를 사용해 함수를 값으로 표현할 수 있다.
// 따라서 코틀린에서의 고차 함수는 람다나 함수 참조를 인자로 넘길 수 있거나, 람다나 함수 참조를 반환하는 함수다.
// 예시로 filter는 술어 함수를 인자로 받으므로 고차함수다.
val list = listOf(1, 2, 3, 4, 5).filter { it % 2 == 0 }

// 고차 함수를 정의하기 위해서 람다 파라미터의 타입을 선언하는 방법을 알아보도록 하자.
// 람다를 정의하면 컴파일러는 함수 타입이라고 자동적으로 추론한다.
val sum = { x: Int, y: Int -> x + y }
val action = { println("Hello, Kotlin!") }

// 이를 각 변수에 구체적인 타입 선언을 추사하면 다음과 같다.
val sumType: (Int, Int) -> Int = { x, y -> x + y }
val actionType: () -> Unit = { println("Hello, Kotlin!") }
// 함수 타입을 정의하려면 함수 파라미터의 타입을 괄호 내에 넣고 그 뒤에 화살표를 추가한 다음, 함수의 반환 타입을 지정하면 된다.
// 파라미터 타입 -> 반환 타입 => (Int, String) -> Unit
// 그냥 함수를 정의할 때에는 Unit 반환을 생략해도 되지만 함수 타입을 선언할 때는 반환 타입을 반드시 명시해야 하므로 Unit을 명시해야 한다.
// 이렇게 함수 타입에 파라미터 타입을 지정했기 때문에 람다 자체에는 파라미터 타입을 지정할 이유가 없는 것이다.

// 함수 타입에서도 반환 타입을 null이 될 수 있는 타입으로 지정항 수 있다.
var canReturnNull: (Int, Int) -> Int? = { x, y -> null }

// 또한 null이 될 수 있는 함수 타입 변수를 정의할 수도 있다.
val funOrNull: ((Int, Int) -> Int)? = null

// 함수 타입을 활용하여 고차 함수를 어떻게 구현할 수 있는지 살펴보자.
fun twoAndThree(operation: (Int, Int) -> Int) {
    val result = operation(2, 3)
    println(result)
}

// 함수 타입에서 파라미터 이름을 지정할 수도 있다.
// 파라미터 이름은 타입 검사 시 무시된다.
// 하지만 코드 가독성과 IDE의 자동 완성의 용이함을 위해서 선언하는 것이 좋을 수 있다.
fun twoAndThreeParameter(oper: (operationA: Int, operationB: Int) -> Int) {
    val result = oper(2, 3)
    println(result)
}

fun callExample() {
    twoAndThree { x, y -> x + y }
    twoAndThreeParameter { x, y -> x + y }
}


