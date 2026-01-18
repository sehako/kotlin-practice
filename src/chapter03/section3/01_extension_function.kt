package chapter03.section3

// 확장 함수 import 예시
// as 활용 시 임포트한 클래스나 함수를 다른 이름으로 활용 가능
// 다른 여러 패키지에 이름이 같은 함수가 많을 때
// 이름을 바꿔 import하면 편리함
// 코틀린 문법 상 짧은 이름이 권장됨
// 따라서 import할 때 이름을 바꾸는 것이 함수의 이름 충돌을 해결하는 유일 방법
import chapter03.section3.util.joinToStringExpandFunction
import chapter03.section3.util.lastCharOutSide as lastCChar

// 어떤 클래스의 멤버 메서드인 것처럼 호출할 수 있지만 그 클래스의 밖에 선언된 함수를 확장 함수
// 확장 함수는 다른 JVM 생태계의 API 재작성 없이도 편리한 여러 기능을 사용하기 위한 코틀린의 문법
// 참고로 어떤 클래스를 확장한 함수와 그 클래스의 멤버 함수의 이름과 시그니처가 같으면 멤버 함수가 호출됨

fun main() {
    // 일반적인 클래스 멤버를 호출하는 구문과 같음
    // 따라서 이는 String 클래스에 메서드를 추가(확장)하는 것과 같음
    println("Kotlin".lastChar())
    println("Kotlin".lastCharWithOutThis())
    println("Kotlin".lastCChar())

    // list에 대한 확장 함수를 통한 joiner 예시
    val list = listOf(1, 2, 3)
    println(list.joinToStringExpandFunction())

    // 확장 함수는 정적 메서드 호출에 대한 문법적인 편의
    // 따라서 구체적인 수신 객체 타입을 명시 가능
    // ex) fun Collection<String>.joinToStringExpandFunction(): String

    // 확장 함수 오버라이드 불가능 예시
    val view: View = Button()
    view.click() // Button Clicked
    // 확장 함수를 첫 번째 인자가 수신 객체인 정적 자바 메서드로 컴파일됨
    // 따라서 호출될 확장 함수를 정적으로 결정하기 때문에 오버라이딩이 적용되지 않음
    view.showOff() // I'm a View!

}

// 문자열의 마지막 문자를 돌려주는 메서드를 추가하는 확장 함수 예시
fun String.lastChar(): Char = this[this.length - 1]
// 추가 함수 이름 앞에 확장할 클래스의 이름을 명시하면 됨 이를 수신 객체 타입이라고 부르며,
// 호출하는 대상 값을 수신 객체라고 부른다. 즉 다음 구조
// fun 수신 객체 타입.함수 이름() = this(수신 객체)

// 일반 메서드 본문에서의 this와 같게 확장 함수 본문에도 this 생략이 가능
fun String.lastCharWithOutThis(): Char = this[length - 1]

// 확장 함수는 캡슐화를 깨지 않는다. public 멤버 이외에는 접근이 불가능하기 때문
class Example(private val value: String)

fun Example.expand(): String = "value 접근 불가능"

// 확장 함수는 오버라이드가 불가능
open class View { // View를 open으로 만들어 하위 클래스 생성 허용
    open fun click() = println("View Clicked")
}

class Button : View() { // Button에서 click()을 오버라이딩
    override fun click() = println("Button Clicked") // 코틀린 오버라이딩 예시
}

// 위 상속 관계에서 각 클래스에 대한 확장 함수 정의
fun View.showOff() = println("I'm a View!")
fun Button.showOff() = println("I'm a Button!")
