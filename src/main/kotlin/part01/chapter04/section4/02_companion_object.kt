package part01.chapter04.section4

// 코틀린 클래스에는 정적 멤버가 없다. (자바 static 키워드를 미지원한다)
// 대신 코틀린에서는 패키지 수준의 최상위 함수와 object를 활용한다.
// 대부분의 경우 최상위 함수를 권장하지만 최상위 함수는 클래스의 private 멤버에 접근이 불가능하다.
// private 멤버에 접근해야 하는 함수의 대표적인 예로 팩토리 메서드가 있다.
// 클래스의 인스턴스와 관계없이 호출해야 하지만,
// 클래스 내부 정보에 접근해야 하는 함수가 필요할 때도 클래스에 내포된 객체 선언의 멤버 함수로 정의할 수 있다.
// 이를 통해 객체 멤버에 접근할 때 자신을 감싸는 클래스의 이름을 통해 직접 사용할 수 있게 된다.
// 즉, 자바의 정적 메서드 호출이나 정적 필드 구문과 같아진다.
class MyClass {
    companion object {
        fun callMe() {
            println("companion object called")
        }
    }
}

// 이 동반 객체(companion object)가 바로 private 생성자를 호출하기 좋은 위치다.
// 동반 객체는 자신을 둘러싼 클래스의 모든 private 멤버에 접근이 가능하다.
// 따라서 팩토리 패턴을 구현하기 가장 적합한 위치가 될 수 있다.
class CompanionUser private constructor(val nickname: String) {
    companion object {
        fun newSubscribingUser(email: String) =
            CompanionUser(email.substringBefore('@'))

        fun newSocialUser(id: Int) = CompanionUser("user$id")
    }
}

// 동반 객체는 클래스에 정의된 일반 객체이다.
// 따라서 동반 객체에 이름을 붙이거나, 클래스 또는 인터페이스를 상속/구현하거나, 확장 함수와 프로퍼티를 정의할 수 있다.

// 동반 객체에 이름 부여
class CompanionPerson(val name: String) {
    // Loader라는 이름의 동반 객체를 생성
    // 참고로 이름 미지정 시 동반 객체의 이름은 자동으로 Companion이 됨
    companion object Loader {
        fun fromJSON(jsonText: String): CompanionPerson? = null
    }
}

// 동반 객체 인터페이스 구현
interface JSONFactory<T> {
    fun fromJSON(jsonText: String): T?
}

class JSONPerson(val name: String) {
    // 동반 객체의 인터페이스 구현
    companion object : JSONFactory<JSONPerson> {
        override fun fromJSON(jsonText: String): JSONPerson? = null
    }
}

// 추상 팩토리를 통해 엔티티를 적재하는 함수가 있아면 JSONPerson 객체를 해당 팩토리에 넘길 수 있다.
fun <T> loadFromJSON(factory: JSONFactory<T>): T {
    return factory.fromJSON("")!!
}

// 동반 객체 확장
class ExpandPerson(val firstName: String, val lastName: String) {
    // 동반 객체 확장을 위한 빈 동반 객체 선언
    companion object
}

// ExpandPerson의 동반 객체에 대한 확장 함수 정의
// 클래스 멤버 함수처럼 보이지만 실제로는 멤버 함수가 아니다.
fun ExpandPerson.Companion.fromJSON(json: String): ExpandPerson? {
    return null
}

fun main() {
    // companion object 호출
    // 클래스의 인스턴스는 동반 객체의 멤버에 접근할 수 없다.
    MyClass.callMe()

//    MyClass().callMe() <- 호출 불가능

    // 동반 객체를 활용한 팩토리 패턴 사용
    // 마찬가지로 클래스의 이름을 사용해 그 클래스에 속한 동반 객체의 메서드를 호출할 수 있다.
    val newSubscribingUser = CompanionUser.newSubscribingUser("")

    // 동반 객체가 구현한 JSONFactory의 인스턴스를 넘길 때 JSONPerson의 이름을 사용했다는 점에 유의하자.
    // 내부적으로 아마 JSONPerson.Companion 이런 식으로 되는 것 같다.
    loadFromJSON(JSONPerson)

    // 확장 함수 사용은 동일함
    ExpandPerson.fromJSON("")
}

/*
자바 사용 목적으로 코틀린 클래스의 멤버를 정적인 멤버로 만들어야 한다면 @JvmStatic을 코틀린 멤버에 선언하면 된다.
정적 필드가 필요하다면 @JvmField를 최상위 프로퍼티나 object에 선언된 프로퍼티 앞에 붙인다.
 */