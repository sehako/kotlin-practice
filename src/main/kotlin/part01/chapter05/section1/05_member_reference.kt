package part01.chapter05.section1

// 이미 넘기려는 코드가 정의된 경우에는 멤버 참조(::)를 활용할 수 있다.
// 이를 활용하면 하나의 메서드를 호출하거나 한 프로퍼티에 접근하는 함수 값을 만들어준다.

class ReferencePerson(val name: String)

val getName = ReferencePerson::name // 이는 다음과 같다. person: ReferencePerson -> person.name

// 참조 대상이 함수인지 프로퍼티인지는 관계없이 멤버 참조 뒤에는 괄호를 넣으면 안 된다.
// 해당 대상을 참조할 뿐이지 호출하는 것이 아니기 때문이다.
// 최상위에 선언된 함수나 프로퍼티를 참주할 수도 있다.
fun greeting() = println("Hello")

fun greetingFunction() {
    // 최상위 선언된 함수나 프로퍼티를 참조할 경우 클래스 이름을 생략하고 바로 ::으로 시작하게 된다.
    run(::greeting)
}

// 람다가 인자가 여럿인 다른 함수에게 작업을 위임하는 경우에는 멤버 참조를 제공하면 편리하다.
fun sendEmail(person: ReferencePerson, message: String) {
    println("Email to ${person.name}: $message")
}

val action = { person: ReferencePerson, message: String -> sendEmail(person, message) }

// 람다 대신 멤버 참조를 쓸 수 있다.
val nextAction = ::sendEmail

// 생성자 참조를 사용하면 클래스 생성 작업을 연기하거나 저장해둘 수 있다. ::뒤에 클래스 이름을 넣으면 생성자 참조를 만들 수 있다.
data class ReferenceUser(val nickname: String, val age: Int)

fun constructorReference() {
    val createUser = ::ReferenceUser
    val p = createUser("a", 20)
    println(p)
}

// 확장 함수도 멤버 함수와 똑같은 방식으로 참조할 수 있다.
fun ReferenceUser.isAdult() = age >= 18
val predicate = ReferenceUser::isAdult

// 멤버 참조 구문을 활용하여 특정 객체 인스턴스에 대한 메서드 호출에 대한 참조를 만들 수 있다. (Bounded Callable Reference)

class CallablePerson(val name: String, val age: Int)

fun personWithAge() {
    val a = CallablePerson("A", 29)
    // 사람이 주어지면 나이를 돌려주는 멤버 참조
    val personAgeFunction = CallablePerson::age
    println(personAgeFunction(a))

    // 값과 엮인 호출 가능 참조
    val aAgeFunction = a::age
    println(aAgeFunction()) // 특정 값과 엮여있으므로 파라미터를 지정할 필요가 없다.
}
