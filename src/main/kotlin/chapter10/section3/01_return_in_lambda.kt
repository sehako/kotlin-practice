package chapter10.section3

// 컬렉션에 대한 이터레이션을 살펴보자.
data class ReturnPerson(val name: String, val age: Int)


fun lookForAlice(people: List<ReturnPerson>) {
    for (person in people) {
        if (person.name == "Alice") {
            println("Found Alice!")
            return
        }
    }

    println("Alice not found")
}

// 위 코드를 forEach 함수를 대신 써도 된다.
fun lookForAliceForEach(people: List<ReturnPerson>) {
    people.forEach {
        if (it.name == "Alice") {
            println("Found Alice!")
            // lookForAliceForEach 함수에서 반환된다.
            return
        }
    }
    println("Alice not found")
}

// 람다 안에서 return을 하면 람다에서만 반환되는 것이 아니라 그 람다를 호출하는 함수가 실행을 끝내고 반환된다.
// 자신을 둘러싼 블록보다 더 바깥에 있는 다른 블록을 반환하게 만드는 것을 비로컬 return이라고 한다.
// 코틀린은 람다를 인자로 받는 함수 안에서 쓰이는 return이 같은 의미를 유지하게 한다.
// 이런 비로컬 return이 가능한 경우는 람다를 인자로 받는 함수가 인라인 함수인 경우뿐이다.
// 람다 본문이 모두 내부적으로 인라이닝되기 때문에 return 식이 함수 자체를 반환하는 형태가 된다.

// 인라인 함수가 아닌 경우에는 return을 람다 본문에 명시할 수 없다.
fun noInlineFunction(operation: (Int) -> Unit) {
    operation(10)
}

fun returnInLambdaExample() {
    noInlineFunction {
//        if (it > 10) return <- 컴파일 에러 발생
        println(it)
    }
}

// 람다식에서도 로컬 return을 사용할 수 있다. 람다에서 로컬 return은 for 반복의 continue와 비슷한 역할을 한다.
// 로컬 return은 람다의 실행을 끝내고 람다를 호출했던 코드의 실행을 이어간다.
fun lookForAliceLocalReturn(people: List<ReturnPerson>) {
    // 람다식 앞에 레이블을 레이블@ 형태로 붙인다.
    people.forEach label@{
        if (it.name != "Alice") return@label // return@label로 정의한 레이블을 참조한다.
        println("Found Alice!!") // return이 실행되지 않은 경우에만 이 줄이 출력된다.
    }

    // 또는 람다를 인자로 받는 인라인 함수의 이름을 레이블로 사용해도 된다.
    people.forEach {
        if (it.name != "Alice") return@forEach // 레이블을 명시하면 해당 방법은 사용할 수 없다.
        println("Found Alice!!")
    }
}

// 일반 함수 예시
fun noInlineLabelReturn() {
    noInlineFunction label@{
        // 이는 람다를 끝낸다고 명시하기 때문에 가능하다.
        if (it > 10) return@label
    }
}

// 이는 this 식의 레이블에도 동일한 규칙이 적용된다.
fun thisLabelExample() {
    StringBuilder().apply sb@{
        listOf(1, 2, 3).apply {
            // this@sb는 StringBuilder 수신 객체, this는 List 수신 객체를 가리킨다.
            this@sb.append(this.toString())
        }
    }
}

